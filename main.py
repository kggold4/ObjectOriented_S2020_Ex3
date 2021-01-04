from src.DiGraph import DiGraph
from src.GraphInterface import GraphInterface
from src.GraphCreator import GraphCreator
from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphAlgo import GraphAlgo


if __name__ == '__main__':
    graph = GraphCreator.create_graph(10, 10, 1)
    graphAlgo = GraphAlgo(graph)
    print('save graph:', graphAlgo.get_graph())
    print(graphAlgo.graph.e_size())
    graphAlgo.save_to_json("graph.json")

    graph2 = DiGraph()
    graphAlgo2 = GraphAlgo(graph2)
    graphAlgo2.load_from_json("graph.json")
    print('load graph:', graphAlgo2.get_graph())
    print(graphAlgo2.graph.e_size())

    graphAlgo2.plot_graph()
