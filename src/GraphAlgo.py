from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph, NodeData
import json
import matplotlib.pyplot as plt
import math
import queue


class GraphAlgo(GraphAlgoInterface):

    def __init__(self, graph: GraphInterface):
        self.graph = graph

    def get_graph(self) -> GraphInterface:
        return self.graph

    def save_to_json(self, file_name: str) -> bool:
        try:
            with open(file_name, 'w') as file:
                json.dump(self.graph, default=lambda m: m.__dict__, indent=4, fp=file)
            return True
        except IOError as e:
            print(e)
            return False

    def load_from_json(self, file_name: str) -> bool:

        # create new graph to self graph
        self.graph = DiGraph()

        # create nodes, childes and parents dictionary for the load graph
        nodes = {}
        childes = {}
        parents = {}

        # trying to read from the json file
        try:
            with open(file_name, 'r') as file:

                # get json of the graph from file
                json_graph = json.load(file)

                # add each node to self graph
                for k, v in json_graph.get('nodes').items():
                    nodes[k] = NodeData(**v)
                self.graph.nodes = nodes

                # add each node childes to self graph
                for k, v in json_graph.get('childes').items():
                    childes[k] = v
                self.graph.childes = childes

                # add each node parents to self graph
                for k, v in json_graph.get('parents').items():
                    parents[k] = v
                self.graph.parents = parents

                # getting the mode count and edge count from the json
                self.graph.mc = json_graph.get("mc")
                self.graph.ec = json_graph.get("ec")

            return True
        except IOError as e:
            print(e)
            return False

    # def shortest_path(self, id1: int, id2: int) -> (float, list):
    #     sheker_kolshehoo = queue.PriorityQueue()
    # def connected_component(self, id1: int) -> list:
    # def connected_components(self) -> List[list]:
    def plot_graph(self) -> None:

        # positions list will contains all the positions of all the nodes in the graph
        positions = {}

        # epsilon for plot texts in the graph
        epsilon = 0.15

        # going throw every node int the graph
        nodes = self.graph.get_all_v()
        for i in nodes.keys():

            # getting node position
            position = nodes[i].get_pos()

            # adding the position to positions list
            positions[i] = position

            # get x and y of the node position
            x = position[0]
            y = position[1]

            # plot the node
            plt.plot(x, y, marker='o', color='b',  markersize=10)

            # each node print near to it the node_id key
            plt.text(x + epsilon, y + epsilon, i, fontsize=10, color='g')

        # for each node print arrow to child
        for i in nodes.keys():

            # get node childes
            childes = self.graph.all_out_edges_of_node(i)

            # go throw node childes
            for j in childes:

                # get source and destination positions
                source = positions[i]
                destination = positions[j]

                # print arrow between source and destination nodes
                plt.arrow(source[0], source[1], destination[0] - source[0], destination[1] - source[1],
                          lw=1, length_includes_head=True, head_width=0.3)

                weight_x = (source[0] + destination[0]) / 2 + epsilon
                weight_y = (source[1] + destination[1]) / 2 + epsilon

                # near each arrow print the weight of the edge
                plt.text(weight_x, weight_y, '%.2f' % childes[j], color='r')

        # tight edge of the board to plot nicer
        plt.tight_layout()

        # show board
        plt.show()
