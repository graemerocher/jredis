/*
 *   Copyright 2009-2010 Joubin Houshyar
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *    
 *   http://www.apache.org/licenses/LICENSE-2.0
 *    
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.jredis.cluster;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * [TODO: document me!]
 *
 * @author  joubin (alphazero@sensesay.net)
 * @date    Mar 24, 2010
 * 
 */

// this is just a datastructure with a few methods -- make it a class
public interface ClusterSpec {

	// ------------------------------------------------------------------------
	// Node/Key space management and distribution 
	// ------------------------------------------------------------------------
	
	// TODO: may wish to use a simple enum here to spec the cluster type (i.e. ConsistentHashing)
	// and let the RIs supply a model to suite it.
//	
//	/**
//	 * @return the stretegy used to distribute keys across the node space.
//	 */
//	public ClusterStrategy  getStrategy();
//	
	public Type getType ();
	public ClusterSpec setType (Type clusterType);
	
	// ------------------------------------------------------------------------
	// Membership
	// ------------------------------------------------------------------------
	/**
	 * @return
	 */
	public Set<ClusterNodeSpec> getNodeSpecs();
	
	/**
	 * @param nodeSpec
	 * @return
	 * @throws IllegalArgumentException if nodeSpec provided is already present.
	 */
	public ClusterSpec addNode(ClusterNodeSpec nodeSpec);
	
	/**
     * @param nodeSpec
	 * @throws IllegalArgumentException if nodeSpec provided is already present.
     */
    public ClusterSpec removeNode (ClusterNodeSpec nodeSpec);
    
	/**
	 * @param nodeSpecs is a {@link Collection} instead of a {@link Set} to 
	 * relax the type requirements, but the required semantics remains set like,
	 * and duplicate (per {@link ClusterNodeSpec#equals(Object)} elements will
	 * be rejected.
	 * @return
	 */
	public ClusterSpec addAll(Collection<ClusterNodeSpec> nodeSpecs);
	
	// ========================================================================
	// INNER CLASSES
	// ========================================================================
	
	public enum Type {
		ConsistentHash
	}
	// ------------------------------------------------------------------------
	// Implementation Support 
	// ------------------------------------------------------------------------
	
	public abstract static class Support implements ClusterSpec {

//		/**  */
//		final protected ClusterModel distributionStrategy;
		private Type type;
		
		/**  */
		final protected Set<ClusterNodeSpec> nodeSpecs = new HashSet<ClusterNodeSpec>();
		
		// ------------------------------------------------------------------------
		// constructor (template) 
		// ------------------------------------------------------------------------
		
		protected Support () { }
		
		// ------------------------------------------------------------------------
		// interface
		// ------------------------------------------------------------------------
		
		public Type getType() { return type; }
		public ClusterSpec setType(Type type) { this.type = type; return this; }
		
		/* (non-Javadoc) @see org.jredis.cluster.ClusterSpec#addAll(java.util.List) */
//        @Override
        public ClusterSpec addAll (Collection<ClusterNodeSpec> nodeSpecs) {
        	for(ClusterNodeSpec nodeSpec : nodeSpecs){
        		addNode(nodeSpec);
        	}
	        return this;
        }

		/* (non-Javadoc) @see org.jredis.cluster.ClusterSpec#addNode(org.jredis.cluster.ClusterNodeSpec) */
//        @Override
        public ClusterSpec addNode (ClusterNodeSpec nodeSpec) {
        	if(null == nodeSpec)
    			throw new IllegalArgumentException("null nodeSpec");
        	
    		if(!this.nodeSpecs.add(nodeSpec))
    			throw new IllegalArgumentException("nodeSpec [id: <"+nodeSpec.getId()+">] is already present");
    		
    		return this;
        }

        /* (non-Javadoc) @see org.jredis.cluster.ClusterSpec#removeNode(org.jredis.cluster.ClusterNodeSpec) */
        public ClusterSpec removeNode (ClusterNodeSpec nodeSpec) {
        	if(null == nodeSpec)
    			throw new IllegalArgumentException("null nodeSpec");
        	
    		if(!this.nodeSpecs.remove(nodeSpec))
    			throw new IllegalArgumentException("nodeSpec [id: <"+nodeSpec.getId()+">] not part of this cluster spec");
    		
    		return this;
        }

		// ------------------------------------------------------------------------
		// accessors
		// ------------------------------------------------------------------------
        
//		
//    	/* (non-Javadoc) @see org.jredis.cluster.ClusterSpec#getDistributionStrategy() */
//    	final public ClusterStrategy  getStrategy() {
//    		return this.distributionStrategy;
//    	}
    	
		/* (non-Javadoc) @see org.jredis.cluster.ClusterSpec#getNodes() */
//        @Override
        final public Set<ClusterNodeSpec> getNodeSpecs () {
        	Set<ClusterNodeSpec> set = new HashSet<ClusterNodeSpec>(nodeSpecs.size());
        	for(ClusterNodeSpec spec : nodeSpecs)
        		set.add(spec);
	        return set;
        }

//		// ------------------------------------------------------------------------
//		// Extension points
//		// ------------------------------------------------------------------------
//        protected abstract ClusterModel newStrategy();
	}

}
