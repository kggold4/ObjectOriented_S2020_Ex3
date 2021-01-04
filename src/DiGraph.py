import numpy as np

from src.GraphInterface import GraphInterface


class DiGraph(GraphInterface):
    """
    default constructor
    """
    def __init__(self):
        self.nodes = {}
        self.childes = {}
        self.parents = {}
        self.ec = 0
        self.mc = 0

    def __repr__(self):
        return str(self.nodes.values()) + str(self.childes)

    """
    :return the size of nodes in the graph
    """
    def v_size(self) -> int:
        return len(self.nodes)

    """
    :return the size of edges in the graph
    """
    def e_size(self) -> int:
        return self.ec

    """
    :return a dictionary of graph nodes
    """
    def get_all_v(self) -> dict:
        return self.nodes

    """
    :param id1 node key
    :return a dictionary of all the nodes connected to id1 node
    """
    def all_in_edges_of_node(self, id1: int) -> dict:
        if id1 in self.nodes:
            return self.parents[id1]

    """
    :param id1 node key
    :return a dictionary of all the nodes connected from id1 node
    """
    def all_out_edges_of_node(self, id1: int) -> dict:
        if id1 in self.nodes:
            return self.childes[id1]

    """
    :return mode count
    """
    def get_mc(self) -> int:
        return self.mc

    """
    connect between two nodes and weight
    :param id1 the node1
    :param id2 the node2
    :param weight the weight of the edge
    :return true if nd1 and nd2 connected successfully
    """
    def add_edge(self, id1: int, id2: int, weight: float) -> bool:

        # cannot be connected
        # id1 or id2 cannot be found int the graph
        if id1 not in self.nodes.keys() or id2 not in self.nodes.keys() or len(self.nodes) <= 1 or id1 == id2:
            return False

        # getting NodeData objects of id1
        node1 = self.nodes.get(id1)

        # if node1 and node2 is connected
        if id2 in self.childes.get(id1):

            # if weight is the same
            if self.childes[id1][id2] == weight:
                return False

            # if weight is not the same
            else:
                self.childes[id1][id2] = weight
                self.parents[id2][id1] = weight
                self.mc += 1
                return True

        # if node1 and node2 is not connected
        else:
            self.childes[id1][id2] = weight
            self.parents[id2][id1] = weight
            self.ec += 1
            self.mc += 1
            return True

    """
    adding node to the graph
    :param node_id the key of the node
    :param pos node position
    :return true if succeed by adding the node
    """
    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        if node_id in self.nodes.keys():
            return False
        else:
            self.childes[node_id] = {}
            self.parents[node_id] = {}
            self.nodes[node_id] = NodeData(node_id, pos)
            self.mc += 1
            return True

    """
    :param node_id
    :return true if succeed by removing the node
    """
    def remove_node(self, node_id: int) -> bool:

        # if node_id cannot be found in the graph
        if node_id not in self.nodes.keys():
            return False
        else:
            # remove any connection with node_id and his childes
            node_childes = self.childes[node_id].keys()
            for i in list(node_childes):
                self.remove_edge(node_id, i)
                # self.parents[i].pop(node_id)

            # remove from childes node_id
            self.childes.pop(node_id)

            # remove any connection with node_id and his parents
            node_parents = self.parents[node_id].keys()
            for i in list(node_parents):
                self.remove_edge(i, node_id)

            # remove from parents node_id
            self.parents.pop(node_id)

            # remove node id from nodes dictionary
            self.nodes.pop(node_id)
            self.mc += 1

    """
    :param node_id1 node key
    :param node_id2 node key
    :return true 
    """
    def remove_edge(self, node_id1: int, node_id2: int) -> bool:

        # if node_id1 or node_id2 cannot be found in the graph
        if node_id1 not in self.nodes.keys() or node_id2 not in self.nodes.keys()\
                or len(self.nodes) <= 1 or node_id1 == node_id2:
            return False
        else:
            self.childes[node_id1].pop(node_id2)
            self.parents[node_id2].pop(node_id1)
            self.ec -= 1
            self.mc += 1
            return True


class NodeData:
    key = None

    def __init__(self, key: int = None, position: tuple = None, weight: float = 0, tag: int = 0, info: str = ""):
        self.key = key
        self.position = position
        self.weight = weight
        self.tag = tag
        self.info = info

    def __repr__(self):
        return str(self.key)

    def get_pos(self) -> tuple:
        return self.position

    def distance(self, node) -> float:
        if self.position is None or node.position is None:
            return -1
        else:
            return np.sqrt((np.power(self.position[0] - node.position[0], 2)) +
                           (np.power(self.position[1] - node.position[1], 2)))


if __name__ == '__main__':
    g = DiGraph()
    g.add_node(1)
    g.add_node(2)
    g.add_node(3)
    g.add_edge(1, 2, 5)
    print(g.all_in_edges_of_node(1))
    print(g.all_out_edges_of_node(1))
