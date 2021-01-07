# use numpy library in distance method
import numpy as np


class NodeData:
    """
    NodeData class represents a node (vertex) in the graph
    """

    def __hash__(self) -> int:
        """
        hashcode method
        :return:
        """
        return super().__hash__()

    def __eq__(self, o: object) -> bool:
        """
        equals method
        :param o:
        :return:
        """
        return super().__eq__(o)

    def __init__(self, key: int = None, position: tuple = None, weight: float = 0,
                 tag: int = 0, info: str = "", scc_key: int = None):
        """
        default constructor
        :param key: node_id
        :param position: node position
        :param weight: node weight - use in shortest path algorithm
        :param tag: node tag - use in string connected components algorithm
        :param info: the node metadata string
        """
        self.key = key
        self.position = position
        self.weight = weight
        self.tag = tag
        self.info = info
        self.scc_key = scc_key

    def __repr__(self):
        """
        :return: node key as a string
        """
        return str(self.key)

    def get_key(self) -> int:
        """
        :return: node key
        """
        return self.key

    def get_pos(self) -> tuple:
        """
        :return: node position
        """
        return self.position

    def distance(self, node) -> float:
        """
        :param node: the other node
        :return: distance between this node and the other node
        """
        if self.position is None or node.position is None:
            return -1
        else:
            return float(np.sqrt((np.power(self.position[0] - node.position[0], 2)) +
                                 (np.power(self.position[1] - node.position[1], 2))))

    def set_scc(self, scc_key: int):
        """
        set the scc_key to this node, use in strong connected components algorithm
        :param scc_key: the scc_key
        :return:
        """
        self.scc_key = scc_key

    def get_scc(self) -> int:
        """
        :return: the scc_key of this node
        """
        return self.scc_key
