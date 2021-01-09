from src import DiGraph
import random


class GraphCreator:
    """
    this class creates randomly graph by given |V| and |E| sizes with a seed number
    """
    # default size
    size = 25

    @staticmethod
    def create_graph(n: int = size, e: int = 0, seed_num: int = 0) -> DiGraph:
        """
        create_graph function is a static function that get n, e and seed_num and create randomly a DiGraph
        :param n: number of nodes
        :param e: number of edges
        :param seed_num: random sead
        :return: the graph that created
        """

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
            p = (pos[i], pos[n - i])
            graph.add_node(i, p)

        # connect between nodes randomly
        graph_nodes = graph.get_all_v()
        edge_counter = 0
        if e > 0:
            while edge_counter < e:
                for i in range(n):

                    # randomly generate float number between 0 and 1, if the random > 0.5 connect to random node
                    if random.random() > 0.5:

                        # getting random node id (not the same node as src)
                        ni = random.randrange(0, n, 1)
                        while ni == i:
                            ni = random.randrange(0, n, 1)

                        # connect between  them
                        graph.add_edge(i, ni, graph_nodes[i].distance(graph_nodes[ni]))
                        edge_counter += 1

                        # found that the number of edges reach to the limit e
                        if edge_counter == e:
                            return graph
        return graph

    @staticmethod
    def get_position() -> tuple:
        """
        :return: a tuple with randomly (x ,y) coordinates
        """
        return random.random() * 10, random.random() * 10
