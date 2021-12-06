from src.GraphInterface import GraphInterface
from src.NodeData import NodeData


class DiGraph(GraphInterface):
    """
    DiGraph implements GraphInterface interface represents a directional weighted graph data structures
    """

    def __eq__(self, o: object) -> bool:
        """
        equals method
        :param o:
        :return:
        """
        return super().__eq__(o)

    def __hash__(self) -> int:
        """
        hashcode method
        :return:
        """
        return super().__hash__()

    def __init__(self):
        """
        default constructor
        """
        self.nodes = {}
        self.childes = {}
        self.parents = {}
        self.ec = 0
        self.mc = 0

    def __repr__(self):
        """
        repr method
        :return: graph string
        """
        return str(self.nodes.values()) + str(self.childes)

    def v_size(self) -> int:
        """
        :return: the size of nodes in the graph
        """
        return len(self.nodes)

    def e_size(self) -> int:
        """
        :return: the size of edges in the graph
        """
        return self.ec

    def get_all_v(self) -> dict:
        """
        :return: a dictionary of graph nodes
        """
        return self.nodes

    def all_in_edges_of_node(self, id1: int) -> dict:
        """
        :param id1: node key
        :return: a dictionary of all the nodes connected to id1 node
        """
        if id1 in self.nodes:
            return self.parents.get(id1)

    def all_out_edges_of_node(self, id1: int) -> dict:
        """
        :param id1: node key
        :return: a dictionary of all the nodes connected from id1 node
        """
        if id1 in self.nodes:
            return self.childes.get(id1)

    def get_mc(self) -> int:
        """
        :return: graph mode count
        """
        return self.mc

    def add_edge(self, id1: int, id2: int, weight: float) -> bool:
        """
        connect between two nodes with weight
        :param id1: the source node
        :param id2: the destination node
        :param weight: the weight of the edge
        :return: true if nd1 and nd2 connected successfully
        """

        # cannot be connected
        # id1 or id2 cannot be found int the graph
        id1 = int(id1)
        id2 = int(id2)
        if id1 not in self.nodes.keys() or id2 not in self.nodes.keys() or len(self.nodes) <= 1 or id1 == id2:
            return False

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
            # print('add new edge between {} and {} with {} weight'.format(id1, id2, weight))
            self.childes[id1][id2] = weight
            self.parents[id2][id1] = weight
            self.ec += 1
            self.mc += 1
            return True

    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        """
        adding node to the graph
        :param node_id: node_id the key of the node
        :param pos: pos node position
        :return: true if succeed by adding the node
        """
        node_id = int(node_id)
        if node_id in self.nodes.keys():
            return False
        else:
            self.childes[node_id] = {}
            self.parents[node_id] = {}
            self.nodes[node_id] = NodeData(node_id, pos)
            self.mc += 1
            return True

    def remove_node(self, node_id: int) -> bool:
        """
        :param node_id:
        :return: true if succeed by removing the node
        """

        node_id = int(node_id)
        # if node_id cannot be found in the graph
        if node_id not in self.nodes.keys():
            return False
        else:
            # remove any connection with node_id and his childes
            node_childes = self.childes[node_id].keys()
            for i in list(node_childes):
                self.remove_edge(node_id, i)

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
            return True

    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        """
        :param node_id1: source node
        :param node_id2: destination node
        :return: true if succeed by removing the connection
        """

        node_id1 = int(node_id1)
        node_id2 = int(node_id2)
        # if node_id1 or node_id2 cannot be found in the graph
        if node_id1 not in self.nodes.keys() or node_id2 not in self.nodes.keys() \
                or len(self.nodes) <= 1 or node_id1 == node_id2:
            return False
        else:
            self.childes[node_id1].pop(node_id2)
            self.parents[node_id2].pop(node_id1)
            self.ec -= 1
            self.mc += 1
            return True
