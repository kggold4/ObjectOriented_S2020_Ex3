from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph, NodeData

# using bfs from external algo class
from src.ExternalAlgo import ExternalAlgo

# using nested list in SCC algorithm
from typing import List

# using matplotlib to plot the graph
import matplotlib.pyplot as plt

# using priority queue in shortest path algorithm
from queue import PriorityQueue

# using json to save and load the graph to/from json file
import json


class GraphAlgo(GraphAlgoInterface):

    """
    default constructor
    :param graph to initialize the graph
    """
    def __init__(self, graph: GraphInterface = None):
        self.graph = graph

    """
    :return GraphInterface the graph of the graph algo class
    """
    def get_graph(self) -> GraphInterface:
        return self.graph

    """
    save the graph to a json file
    :param file_name the name of the json file to save the graph to
    :return true if succeed save to file, otherwise false
    """
    def save_to_json(self, file_name: str = 'graph.json') -> bool:
        try:
            with open(file_name, 'w') as file:
                json.dump(self.graph, default=lambda m: m.__dict__, indent=4, fp=file)
            return True
        except IOError as e:
            print(e)
            return False

    """
    load the graph from a json file
    :param filename the name of the json file to load the graph from
    :return true if succeed load from file, otherwise false
    """
    def load_from_json(self, file_name: str = 'graph.json') -> bool:

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

    """
    calculate the shortest path between to nodes
    :param id1 the source node
    :param id2 the destination node
    :return (float, list) the weight between id1 and id2 nodes and the list of the path from id1 to id2
    """
    def shortest_path(self, id1: int, id2: int) -> (float, list):

        # if graph is empty from nodes
        if self.graph.v_size() == 0:
            return None

        # getting all graph nodes dictionary
        nodes = self.graph.get_all_v()

        # check if one of the nodes cannot be found in the graph
        if id1 not in nodes.keys() or id2 not in nodes.keys():
            return None

        # getting start and end nodes
        start = nodes[id1]
        end = nodes[id2]

        # if id1 and id2 is the same node
        if id1 == id2:
            return 0, id1

        # if the nodes in the graph and not the same node but the graph does not have any edges
        if self.graph.e_size() == 0:
            return None

        # set all weights of all nodes to infinity
        for node in nodes.values():
            node.weight = float('inf')

        # set all tags of all nodes to 0
        for node in nodes.values():
            node.tag = 0

        # using priority queue for the shortest path algorithm
        pq = PriorityQueue()

        # previous dictionary will contains for each node id the previous node id in the shortest path
        previous = {}

        # insert the first node id in nodes dictionary to the priority queue
        start.weight = 0.0
        pq.put(start.key)

        # dijkstra's algorithm
        while not pq.empty():

            # poll the shortest priority node, set as visited
            node: NodeData = nodes[pq.get()]

            # calculate the shortest weight between node to start node
            childes = self.graph.all_out_edges_of_node(node.key)
            if len(childes) != 0:
                for child in childes:
                    dist = node.weight + childes[child]
                    if float(dist) < float(nodes[child].weight):
                        # setting new shortest weight, add to queue and set previous
                        nodes[child].weight = dist
                        pq.put(child)
                        previous[child] = node

        # path list from id1 node to id2 node
        path = []

        # going from end to start nodes for build the path
        k = end
        path.append(k)
        while k is not start:

            # if the nodes are in the graph but no valid path between them
            if k.key not in previous.keys():
                return None

            # go to previous node
            k = previous[k.key]
            path.append(k)

        # reversing the path from [end -> ... -> start] to [start -> ... -> end]
        path.reverse()

        # return the distance between start and end nodes (the weight of the end node) and the path
        return end.weight, path

    def connected_component(self, id1: int) -> list:

        # if id1 node is not in the graph return empty list
        if id1 not in self.graph.get_all_v().keys():
            return []

        node_component = []

        # using bfs on id1 as src
        ExternalAlgo.bfs(id1, False, self.graph)

        # each edge the reachable from src add to component
        for node in self.graph.get_all_v().values():
            if node.tag != -1:
                node_component.append(node)

        # using bfs on id1 as src in another direction
        ExternalAlgo.bfs(id1, True, self.graph)

        # delete from component all the nodes that not reachable
        node_component = [node for node in node_component if node.tag != -1]

        # for each node in the component update his scc_key to id1 key
        for node in node_component:
            node.set_scc(id1)
        return node_component

    def connected_components(self) -> List[list]:

        # if graph is empty return an empty list
        if self.graph.v_size() == 0:
            return []

        # the main graph_component list for all nodes components list
        graph_component = []

        # reset all the nodes scc_key to None
        for node in self.graph.get_all_v().values():
            node.set_scc(None)

        # for each node if the scc_key is equal to Node use connected_components method to check his scc_key
        for node in self.graph.get_all_v().values():
            if node.scc_key is None:
                node_component = self.connected_component(node.key)

                # add node_component to graph_component
                graph_component.append(node_component)

        # return graph_component that contains all node_component of the graph
        return graph_component

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
            plt.plot(x, y, marker='o', color='b', markersize=10)

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
