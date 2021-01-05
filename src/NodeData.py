import numpy as np


class NodeData:
    def __init__(self, key: int = None, position: tuple = None, weight: float = 0, tag: int = 0, info: str = ""):
        self.connected_component =  None
        self.key = key
        self.position = position
        self.weight = weight
        self.tag = tag
        self.info = info

    def __repr__(self):
        return str(self.key)

    def get_key(self) -> int:
        return self.key

    def get_pos(self) -> tuple:
        return self.position

    def distance(self, node) -> float:
        if self.position is None or node.position is None:
            return -1
        else:
            return np.sqrt((np.power(self.position[0] - node.position[0], 2)) +
                           (np.power(self.position[1] - node.position[1], 2)))

    def set_connected_component(self, key: int):
        """
            set the SCC for this node
            @param key: The representive node of the SCC
        """
        self.connected_component = key