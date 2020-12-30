from src.GraphInterface import GraphInterface


class DiGraph(GraphInterface):
    # constructor
    def __init__(self):
        print("graph created")
        # nodes dict = {key: node_data}
        self.nodes = dict
        self.edges = dict
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
        if node_id in self.nodes:
            return False
        else:
            self.nodes[node_id] = NodeData(node_id, pos)
            self.mc += 1
            return True

    def remove_node(self, node_id: int) -> bool:
        print("remove node")

    def remove_edge(self, node_id1: int, node_id2: int) -> bool:
        print("remove edge")


class NodeData:
    def __init__(self, key: int, position: tuple = None, weight: float = 0, tag: int = 0, info: str = ""):
        self.key = key
        self.position = position
        self.weight = weight
        self.tag = tag
        self.info = info

    def __repr__(self):
        return '' + self.key

    def get_key(self) -> int:
        return self.key

    def get_position(self) -> tuple:
        return self.position

    def get_weight(self) -> float:
        return self.weight

    def get_tag(self) -> int:
        return self.tag

    def get_info(self) -> str:
        return self.info

    def set_position(self, position: tuple):
        self.position = position

    def set_weight(self, weight: float):
        self.weight = weight

    def set_tag(self, tag: int):
        self.tag = tag

    def set_info(self, info: str):
        self.info = info


if __name__ == '__main__':
    t = {"a": 2, "b": 3, "c": 6}
    # list
    x = [1, 2, 3]
    y = (1, 2, 3)
    print(len(t))
