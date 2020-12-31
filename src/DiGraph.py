from src.GraphInterface import GraphInterface


class DiGraph(GraphInterface):
    """
    default constructor
    """
    def __init__(self):
        self.nodes = {}
        self.edges = {}
        self.mc = 0

    """
    :return the size of nodes in the graph
    """
    def v_size(self) -> int:
        return len(self.nodes)

    """
    :return the size of edges in the graph
    """
    def e_size(self) -> int:
        return len(self.edges)

    """
    :return a dictionary of graph nodes
    """
    def get_all_v(self) -> dict:
        return self.nodes

    """
    :return a dictionary of all the nodes connected to id1 node
    :param id1 node
    """
    def all_in_edges_of_node(self, id1: int) -> dict:
        if id1 in self.nodes:
            return self.nodes[id1].get_parents

    """
    :return a dictionary of all the nodes connected from id1 node
    :param id1 node
    """
    def all_out_edges_of_node(self, id1: int) -> dict:
        if id1 in self.nodes:
            return self.nodes[id1].get_childes

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
        if id1 not in self.nodes.keys() or id2 not in self.nodes.keys() or len(self.nodes) <= 1:
            return False

        # getting NodeData objects of id1 and id2
        node1 = self.nodes.get(id1)
        node2 = self.nodes.get(id2)

        # if node1 and node2 is connected
        if node1.is_connected(id2):

            # if weight is the same
            if node1.childes[id2] == weight:
                return False

            # if weight is not the same
            else:
                node1.childes[id2] = weight

        # if node1 and node2 is not connected
        else:
            node1.add_child(id2, weight)
            node2.add_parent(id1, weight)
            return True

    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        if node_id in self.nodes.keys():
            return False
        else:
            self.nodes[node_id] = NodeData(node_id, pos)
            self.mc += 1
            return True

    def remove_node(self, node_id: int) -> bool:
        if node_id not in self.nodes.keys():
            return False
        else:
            current_node = self.nodes[node_id]
            for node in self.nodes.values():
                if node.is_connected(node_id):
                    node.remove_edge(node_id)
                if current_node.is_connected(node.key):
                    self.remove_edge(node.key)
            self.nodes.pop(node_id)

    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        if node_id1 not in self.nodes.keys() or node_id2 not in self.nodes.keys() or len(self.nodes) <= 1:
            return False
        else:
            return self.nodes[node_id1].del_child(node_id2)


class NodeData:
    def __init__(self, key: int = None, position: tuple = None, weight: float = 0, tag: int = 0, info: str = ""):
        self.key = key
        self.position = position
        self.weight = weight
        self.tag = tag
        self.info = info
        self.parents = {}
        self.childes = {}

    def __repr__(self):
        return str(self.key)

    def add_parent(self, key: int, weight: float):
        if key in self.parents.keys():
            return False
        else:
            self.parents[key] = weight
            return True

    def del_parent(self, key: int) -> bool:
        if key in self.parents.keys():
            self.parents.pop(key)
            return True
        else:
            return False

    def get_parents(self) -> dict:
        return self.parents

    def add_child(self, key: int, weight: float):
        if key in self.childes.keys():
            return False
        else:
            self.childes[key] = weight
            return True

    def del_child(self, key: int) -> bool:
        if key in self.childes.keys():
            self.childes.pop(key)
            return True
        else:
            return False

    def get_childes(self) -> dict:
        return self.childes

    def is_connected(self, key: int) -> bool:
        if key in self.childes.keys():
            return True
        else:
            return False


if __name__ == '__main__':
    g = DiGraph()
    g.add_node(1)
    g.add_node(2)
    g.add_node(2)
    g.add_node(3)
    g.add_edge(1, 2, 5.2)
    print()
