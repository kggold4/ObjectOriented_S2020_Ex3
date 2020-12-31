from src.GraphInterface import GraphInterface


class DiGraph(GraphInterface):
    # constructor
    def __init__(self):
        print("graph created")
        # nodes dict = {key: node_data}
        self.nodes = {}
        self.edges = {}
        self.mc = 0

    # return size of nodes
    def v_size(self) -> int:
        return len(self.nodes)

    # return size of edges
    def e_size(self) -> int:
        return len(self.edges)

    # return dictionary of graph nodes
    def get_all_v(self) -> dict:
        return self.nodes

    def all_in_edges_of_node(self, id1: int) -> dict:
        print("all_in_edges_of_node")

    def all_out_edges_of_node(self, id1: int) -> dict:
        print("all_out_edges_of_node")

    # return mode count
    def get_mc(self) -> int:
        return self.mc

    def add_edge(self, id1: int, id2: int, weight: float) -> bool:
        print("add_edge")

    def add_node(self, node_id: int, pos: tuple = None) -> bool:
        if node_id in self.nodes.keys():
            print('node {} cannot add'.format(node_id))
            return False
        else:
            self.nodes[node_id] = NodeData(node_id, pos)
            self.mc += 1
            print('node {} add'.format(node_id))
            return True

    def remove_node(self, node_id: int) -> bool:
        print("remove node")

    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        print("remove edge")


class NodeData(object):
    pass


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

    def add_parent(self, node: NodeData):
        if node.key in self.parents.keys():
            return False
        else:
            self.parents[node.key] = node
            return True

    def get_parent(self, key: int) -> NodeData:
        return self.parents[key]

    def add_child(self, node: NodeData):
        if node.key in self.childes.keys():
            return False
        else:
            self.childes[node.key] = node
            return True

    def get_child(self, key: int) -> NodeData:
        return self.childes[key]


if __name__ == '__main__':
    n0 = NodeData(0)
    n1 = NodeData(1)
    n0.add_child(n1)
    n0.add_parent(n1)
    n1.add_child(n0)
    n1.add_parent(n0)
    x = n0.get_parent(1)
    print(x)
    print(n1 == x)
    # g = DiGraph()
    # g.add_node(1)
    # g.add_node(2)
    # g.add_node(2)
    # g.add_node(3)
