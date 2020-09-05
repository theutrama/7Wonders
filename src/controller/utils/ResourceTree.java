package controller.utils;

import java.util.ArrayList;

import model.card.Resource;

public class ResourceTree {
	/** single inner tree */
	private InnerResourceTree tree;
	/** leaves */
	private ArrayList<InnerResourceTree> leaves;

	/** create a tree with a base resource of zero for all types */
	public ResourceTree() {
		this(new ResourceBundle());
	}

	/**
	 * create a tree with a base requirement
	 * 
	 * @param initial initial requirement
	 */
	public ResourceTree(ResourceBundle initial) {
		tree = new InnerResourceTree(initial);
		leaves = new ArrayList<>();
		leaves.add(tree);
	}

	/**
	 * add list of resources
	 * 
	 * @param resources list of resources
	 */
	public void addResourceOption(ArrayList<Resource> resources) {
		if (resources.isEmpty())
			return;
		ArrayList<InnerResourceTree> newLeaves = new ArrayList<>();
		for (InnerResourceTree child : leaves) {
			for (Resource resource : resources) {
				InnerResourceTree newChild = new InnerResourceTree(new ResourceBundle(resource));
				child.addChild(newChild);
				newLeaves.add(newChild);
			}
		}

		leaves = newLeaves;
	}

	/**
	 * get all resource combinations
	 * 
	 * @return list of all resource combinations
	 */
	public ArrayList<ResourceBundle> getAllCombinations() {
		ArrayList<ResourceBundle> result = new ArrayList<>();
		leaves.forEach(tree -> result.add(tree.resourceSum));
		return result;
	}

	/**
	 * get all possible lists of resources
	 * 
	 * @return list of all resource combinations
	 */
	public ArrayList<ArrayList<ResourceBundle>> getAllCombinationsAsList() {
		ArrayList<ArrayList<ResourceBundle>> result = new ArrayList<>();
		for (InnerResourceTree tree : leaves) {
			ArrayList<ResourceBundle> list = new ArrayList<>();
			while (tree != null) {
				list.add(tree.resource);
				tree = tree.parent;
			}
			result.add(list);
		}
		return result;
	}

	/**
	 * inner class to modulate the tree
	 */
	private static class InnerResourceTree {
		/** list of children */
		private ArrayList<InnerResourceTree> children;
		/** parent tree */
		private InnerResourceTree parent;
		/** resource held by this tree */
		private ResourceBundle resource;
		/** sum of resources of this tree and all parent trees */
		private ResourceBundle resourceSum;

		/**
		 * create an inner tree with a resource
		 * 
		 * @param resource the resource
		 */
		private InnerResourceTree(ResourceBundle resource) {
			this.resource = resource;
			resourceSum = resource;
			children = new ArrayList<>();
		}

		/**
		 * Adds a child to this inner tree. The specified tree's resource is increased by the resource of this tree.
		 * 
		 * @param tree inner tree to become a child of this tree
		 */
		private void addChild(InnerResourceTree tree) {
			children.add(tree);
			tree.resourceSum = resource.add(tree.resourceSum);
			tree.parent = this;
		}
	}

}
