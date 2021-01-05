from src.DiGraph import DiGraph
from queue import SimpleQueue


class ExternalAlgo:
    @staticmethod
    def bfs(node_key: int, upside_down: bool, graph: DiGraph):

        # set all nodes tag to -1
        for node in graph.get_all_v().values():
            node.tag = - 1

        # using simple queue for the bfs algorithm
        queue = SimpleQueue()
        src = graph.get_all_v().get(node_key)
        src.tag = 0
        queue.put(src)
        while not queue.empty():
            node_temp = queue.get()
            # graph as is
            if upside_down is False:
                neighbors = graph.all_out_edges_of_node(node_temp.key)
            # graph transpose
            else:
                neighbors = graph.all_in_edges_of_node(node_temp.key)
            for key in neighbors:
                node_neighbor = graph.get_all_v().get(key)
                if node_neighbor.tag == -1:  # the first time this node is reached
                    node_neighbor.tag = node_temp.tag + 1
                    queue.put(node_neighbor)