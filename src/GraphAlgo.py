from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph
import json


class GraphAlgo(GraphAlgoInterface):

    def __init__(self, graph: GraphInterface):
        self.graph = graph

    def get_graph(self) -> GraphInterface:
        return self.graph

    def save_to_json(self, file_name: str) -> bool:
        try:
            # data = {}
            # data['nodes'] = {}
            # data['parents'] = {}
            # data['childes'] = {}
            # nodes = self.graph.get_all_v()
            # for i in nodes.keys():
            #     data['nodes'][i] = nodes[i]
            # parents = self.graph.all_in_edges_of_node(i)
            # for j in parents.keys():
            #     data['parents'][j] = parents[j]
            # childes = self.graph.all_out_edges_of_node(i)
            # for j in childes.keys():
            #     data['childes'][j] = childes[j]
            # data['mc'] = self.graph.get_mc()
            # data['ec'] = self.graph.e_size()

            with open(file_name, 'w') as file:
                json.dump(self.graph, default=lambda m: m.__dict__, indent=4, fp=file)
            return True
        except IOError as e:
            print(e)
            return False

    # def load_from_json(self, file_name: str) -> bool:
    # def shortest_path(self, id1: int, id2: int) -> (float, list):
    # def connected_component(self, id1: int) -> list:
    # def connected_components(self) -> List[list]:
    # def plot_graph(self) -> None:
