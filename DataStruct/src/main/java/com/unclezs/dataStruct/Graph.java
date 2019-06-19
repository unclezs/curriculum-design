package com.unclezs.dataStruct;

/*
 *无向带权图
 *@author unclezs.com
 *@date 2019.06.17 22:47
 */
public class Graph<E> {
    private MyList<MyList<E>> edges;//边
    private MyList<String> vertexs;//顶点
    private int numOfEdges;//多少条有效边

    //默认10顶点
    public Graph() {
        this(10);
    }

    //按照定点个数来创建图
    public Graph(int n) {
        edges = new MyList<>(n);
        for (int i = 0; i < n; i++) {
            edges.add(new MyList<>(n));
        }
        vertexs = new MyList<>(n);
        numOfEdges = 0;
    }

    //获取多少个定点
    public int getVertextsSize() {
        return vertexs.size();
    }

    //获取多少条边
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //获取指定下标的顶点,越界则返回null
    public String getVertex(int index) {
        if (index > vertexs.size()) {
            return null;
        }
        return vertexs.get(index);
    }

    //显示邻接矩阵
    public void showGraph() {
        for (int i = 0; i < edges.size(); i++) {
            System.out.println(edges.get(i).toString());
        }
    }

    //获取权值
    public E getWeight(int from, int to) {
        return edges.get(from).get(to);
    }

    //插入定点
    public void insertVertex(String v) {
        if (!vertexs.isExist(v))
            vertexs.add(v);
    }

    //添加边
    public void insertEdge(int from, int to, E o) {
        edges.get(from).set(to, o);
        edges.get(to).set(from, o);
        numOfEdges++;
    }

    //获取邻接矩阵
    public MyList<MyList<E>> getEdges() {
        return edges;
    }

    //获取所有顶点
    public MyList<String> getVertexs() {
        return vertexs;
    }

    //移除顶点
    public void removeVertex(int index) {
        if (index >= 0 && index < vertexs.size())
            vertexs.remove(index);
    }

    /**
     * 移除某个顶点所有线路
     *
     * @param vindex 顶点下标
     */
    public void removeAllEdges(int vindex) {
        for (int i = 0; i < edges.size(); i++) {
            edges.get(vindex).set(i, null);
            edges.get(i).set(vindex, null);
        }
    }
    /**
     * 移除一条边
     * @param from
     * @param to
     */
    public void removeAEdege(int from, int to){
        edges.get(from).set(to,null);
        edges.get(to).set(from, null);
        numOfEdges--;
    }

}


