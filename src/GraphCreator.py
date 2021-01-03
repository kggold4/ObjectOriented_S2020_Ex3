from src import DiGraph
import random


class GraphCreator:
    size = 25

    @staticmethod
    def create_graph(n: int = size, e: int = 0, seed_num: int = 0) -> DiGraph:

        # graph seed number
        random.seed(seed_num)

        # make n * 2 points for the node positions
        pos = []
        for i in range(n * 2):
            pos.append(random.random() * 10)

        # create a new graph
        graph = DiGraph.DiGraph()

        # create in the graph n nodes with a tuple (x,y) for a position from pos list
        for i in range(n):
            if i % 2 == 0:
                p = (pos[i], pos[i + 1])
            graph.add_node(i, p)

        edge_counter = 0
        if e > 0:
            while edge_counter < e:
                for i in range(n):
                    rand = random.random()
                    if rand > 0.5:
                        ni = random.randrange(0, n, 1)
                        while ni == i:
                            ni = random.randrange(0, n, 1)
                        graph.add_edge(i, ni, random.random() * 10)
                        edge_counter += 1
                        if edge_counter == e:
                            break
        return graph
