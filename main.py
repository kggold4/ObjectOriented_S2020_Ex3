from src.DiGraph import DiGraph
from src.GraphInterface import GraphInterface
from src.GraphCreator import GraphCreator
from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphAlgo import GraphAlgo


if __name__ == '__main__':
    graph = GraphCreator.create_graph(20, 10, 2)
    graphAlgo = GraphAlgo(graph)
    print('graph:', graphAlgo.get_graph())
    # print('path:', graphAlgo.shortest_path(0, 15))
    main_list = graphAlgo.connected_components()
    graphAlgo.plot_graph()

    # graphAlgo.save_to_json("graph.json")

    # graph2 = DiGraph()
    # graphAlgo2 = GraphAlgo(graph2)
    # graphAlgo2.load_from_json("graph.json")
    # print('load graph:', graphAlgo2.get_graph())
    # print(graphAlgo2.graph.e_size())
    #
    # graphAlgo2.plot_graph()
