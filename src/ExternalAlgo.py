from src.DiGraph import DiGraph
from queue import SimpleQueue


class ExternalAlgo:
    """
    this class contains some external algorithms for use in GraphAlgo class on DiGraphs
    """
    @staticmethod
    def bfs(key: int, direction: bool, graph: DiGraph):
        """
        BFS algorithm
        :param key: source node
        :param direction: search by parents of by childes
        :param graph: the graph use the algorithm
        :return:
        """

        # set all nodes tag to -1
        for node in graph.get_all_v().values():
            node.tag = - 1

        # using simple queue for the bfs algorithm
        queue = SimpleQueue()

        # getting src node from the given key
        source = graph.get_all_v().get(key)

        # set tag of the source to 0 and add to the queue
        source.tag = 0
        queue.put(source)

        # BFS algorithm
        while not queue.empty():
            pop = queue.get()

            # what direction to go (childes or parents)
            if direction is False:
                neighbors = graph.all_out_edges_of_node(pop.key)
            else:
                neighbors = graph.all_in_edges_of_node(pop.key)

            # going throw each neighbor of pop node
            for neighbor in neighbors:

                # getting the neighbor node
                neighbor_node = graph.get_all_v().get(neighbor)

                # if have not been visited yet
                if neighbor_node.tag == -1:

                    # append 1 to the tag and add to the simple queue
                    neighbor_node.tag = pop.tag + 1
                    queue.put(neighbor_node)
