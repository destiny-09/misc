package top.hiccup.algorithm.graph;

import java.util.LinkedList;

/**
 * 最短路径，一个顶点到另一个顶点就是单源最短路径
 * 
 * Dijkstra 时间复杂度O(E*logV)
 *
 * @author wenhy
 * @date 2019/7/25
 */
public class ShortestPath {

    public class Graph {
        /**
         * 有向有权图的邻接表表示
         */
        private LinkedList<Edge> adj[];
        /**
         * 顶点个数
         */
        private int v;

        public Graph(int v) {
            this.v = v;
            this.adj = new LinkedList[v];
            for (int i = 0; i < v; ++i) {
                this.adj[i] = new LinkedList<>();
            }
        }

        public void addEdge(int s, int t, int w) { // 添加一条边
            this.adj[s].add(new Edge(s, t, w));
        }

        private class Edge {
            // 边的起始顶点编号
            public int sid;
            // 边的终止顶点编号
            public int tid;
            // 权重
            public int w;

            public Edge(int sid, int tid, int w) {
                this.sid = sid;
                this.tid = tid;
                this.w = w;
            }
        }

        /**
         * dijkstra 实现使用
         */
        private class Vertex {
            // 顶点编号 ID
            public int id;
            // 从起始顶点到这个顶点的距离
            public int dist;

            public Vertex(int id, int dist) {
                this.id = id;
                this.dist = dist;
            }
        }


        // 因为 Java 提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个
        private class PriorityQueue { // 根据 vertex.dist 构建小顶堆
            private Vertex[] nodes;
            private int count;

            public PriorityQueue(int v) {
                this.nodes = new Vertex[v + 1];
                this.count = v;
            }

            public Vertex poll() {
                return null;
            }

            public void add(Vertex vertex) {
            }

            // 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度 O(logn)。
            public void update(Vertex vertex) {
            }

            public boolean isEmpty() {
                return false;
            }

            public void dijkstra(int s, int t) { // 从顶点 s 到顶点 t 的最短路径
                int[] predecessor = new int[this.count]; // 用来还原最短路径
                Vertex[] vertexes = new Vertex[this.count];
                for (int i = 0; i < this.count; ++i) {
                    vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
                }
                PriorityQueue queue = new PriorityQueue(this.count);// 小顶堆
                boolean[] inqueue = new boolean[this.count]; // 标记是否进入过队列
                vertexes[s].dist = 0;
                queue.add(vertexes[s]);
                inqueue[s] = true;
                while (!queue.isEmpty()) {
                    Vertex minVertex = queue.poll(); // 取堆顶元素并删除
                    if (minVertex.id == t) {
                        break; // 最短路径产生了
                    }
                    for (int i = 0; i < adj[minVertex.id].size(); ++i) {
                        Edge e = adj[minVertex.id].get(i); // 取出一条 minVetex 相连的边
                        Vertex nextVertex = vertexes[e.tid]; // minVertex-->nextVertex
                        if (minVertex.dist + e.w < nextVertex.dist) { // 更新 next 的 dist
                            nextVertex.dist = minVertex.dist + e.w;
                            predecessor[nextVertex.id] = minVertex.id;
                            if (inqueue[nextVertex.id] == true) {
                                queue.update(nextVertex); // 更新队列中的 dist 值
                            } else {
                                queue.add(nextVertex);
                                inqueue[nextVertex.id] = true;
                            }
                        }
                    }
                }
                // 输出最短路径
                System.out.print(s);
                print(s, t, predecessor);
            }

            private void print(int s, int t, int[] predecessor) {
                if (s == t) {
                    return;
                }
                print(s, predecessor[t], predecessor);
                System.out.print("->" + t);
            }

        }
    }
}