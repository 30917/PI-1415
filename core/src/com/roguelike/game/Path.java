package com.roguelike.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Path implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 949741494913268122L;
	
	Level level;
	Node[][] m;
	Unit unit;
	List<Node> path;
	
	public class Node implements Comparable<Node>, Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 3021353777678243327L;
		Place place;
		int d;
		Node p;
		
		public Node(Place pl){
			place = pl;
			d = 100000;
			p = null;
		}

		@Override
		public int compareTo(Node n) {
			return Integer.compare(this.d, n.d);
		}
	}
	
	public Path(Level l, Unit u){
		level = l;
		int len = l.map[0].length;
		m = new Node[len][len];
		for(int i = 0; i<len; i++){
			for(int j = 0; j<len;j++){
				m[i][j] = new Node(l.map[i][j]);
			}
		}
		unit = u;
		m[u.position[0]][u.position[1]].d = 0;
	}
	
	public void updatePath(Level l){
		level = l;
//		//maybe there is a fast way to calc
//		// a path based on the previous one
//		Place[][] G = l.map.clone();
//		int len = G[0].length;
//		int[][] d= new int[len][len];
//		Place[][] p= new Place[len][len];
//		for(int i = 0; i<len; i++){
//			for(int j = 0; j<len;j++){
//				d[i][j] = 1000;
//				p[i][j] = null;
//			}
//		}
//		
//		d[unit.position[0]][unit.position[1]] = 0;
//		
//		Stack<Place> S = new Stack<Place>();
		
		m[unit.position[0]][unit.position[1]].d = 0;
		
		Node n = m[unit.position[0]][unit.position[1]];
		
		PriorityQueue<Node> Q = new PriorityQueue<Path.Node>();
		Q.add(n);
		
		while (!Q.isEmpty()) {
		    Node u = Q.poll();
            for (Place e : u.place.links){
            	if(e==null)
            		continue;
                Node v = m[e.position[0]][e.position[1]];
                int weight = 1; 						//peso do caminho
                int distanceThroughU = u.d + weight;
                //System.out.println(distanceThroughU+" "+v.d);
				if (distanceThroughU < v.d) {
				    Q.remove(v);
				    v.d = distanceThroughU ;
				    v.p = u;
				    Q.add(v);
				}
            }
		}
	}
	
    public void getPathTo(Unit u){
    	Node target = this.m[u.position[0]][u.position[1]];
        path = new ArrayList<Node>();
        for (Node vertex = target; vertex != null; vertex = vertex.p)
            path.add(vertex);
        Collections.reverse(path);
        path.remove(0);
//        System.out.println(path.size());
//        System.out.println(path.get(0).place.position[0]+" - "+path.get(0).place.position[1]);
//        System.out.println(path.get(path.size()-1).place.position[0]+" - "+path.get(path.size()-1).place.position[1]);
    }
}
