from src.DiGraph import DiGraph
from src.GraphInterface import GraphInterface
from src.GraphCreator import GraphCreator
from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphAlgo import GraphAlgo


if __name__ == '__main__':
    graph = GraphCreator.create_graph(10, 15, 4)
    graphAlgo = GraphAlgo(graph)
    graphAlgo.save_to_json("graph.json")

    graphAlgo1 = GraphAlgo()
    graphAlgo1.load_from_json("graph.json")

    graphAlgo1.plot_graph()
