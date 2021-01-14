from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph, NodeData
import math
import json

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
    GraphAlgo class implements GraphAlgoInterface interface
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

    def __init__(self, graph: GraphInterface = None):
        """
        default constructor
        :param graph: initialize the graph
        """
        if graph is None:
            self.graph = DiGraph()
        else:
            self.graph = graph

    def get_graph(self) -> GraphInterface:
        """
        :return: the init graph
        """
        return self.graph

    def save_to_json(self, file_name: str = 'graph.json') -> bool:
        """
        save the graph to a json file
        :param file_name: the name of the json file to save the graph to
        :return: true if succeed save to file, otherwise false
        """
        try:
            with open(file_name, 'w') as file:
                Nodes = []
                Edges = []
                for k, v in self.graph.get_all_v().items():
                    if v.position is not None:
                        print(v.position)
                        Nodes.append({"id": k, "pos": v.position})
                    else:
                        Nodes.append({"id": k})
                    destination = self.graph.all_out_edges_of_node(k)
                    if len(destination) > 0:
                        for d, w in destination.items():
                            Edges.append({
                                "src": k,
                                "w": w,
                                "dest": d
                            })
                save = {"Edges": Edges, "Nodes": Nodes}
                json.dump(save, default=lambda m: m.__dict__, indent=4, fp=file)
            return True
        except IOError as e:
            print(e)
            return False

    def load_from_json(self, file_name: str = 'graph.json') -> bool:
        """
        load the graph from a json file
        :param file_name: the name of the json file to load the graph from
        :return: true if succeed load from file, otherwise false
        """

        # create new graph to self graph
        self.graph = DiGraph()

        # trying to read from the json file
        try:
            with open(file_name, 'r') as file:

                # get json of the graph from file
                json_graph = json.load(file)

                Nodes = json_graph.get("Nodes")

                for n in Nodes:
                    if len(n) > 1:
                        self.graph.add_node(node_id=n["id"], pos=n["pos"])
                    else:
                        self.graph.add_node(n["id"])

                Edges = json_graph.get("Edges")

                for e in Edges:
                    self.graph.add_edge(id1=e["src"], id2=e["dest"], weight=e["w"])

            return True
        except IOError as e:
            print(e)
            return False

    def shortest_path(self, id1: int, id2: int) -> (float, list):
        """
        compute the shortest path between to nodes
        :param id1: the source node
        :param id2: the destination node
        :return: (float, list) the weight between id1 and id2 nodes and the list of the path from id1 to id2
        """

        # if graph is empty from nodes
        if self.graph.v_size() == 0:
            return float('inf'), []

        # getting all graph nodes dictionary
        nodes = self.graph.get_all_v()

        # check if one of the nodes cannot be found in the graph
        if id1 not in nodes.keys() or id2 not in nodes.keys():
            return float('inf'), []

        # getting start and end nodes
        start = nodes[id1]
        end = nodes[id2]

        # if id1 and id2 is the same node
        if id1 == id2:
            return 0, id1

        # if the nodes in the graph and not the same node but the graph does not have any edges
        if self.graph.e_size() == 0:
            return float('inf'), []

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
                return float('inf'), []

            # go to previous node
            k = previous[k.key]
            path.append(k)

        # reversing the path from [end -> ... -> start] to [start -> ... -> end]
        path.reverse()

        # return the distance between start and end nodes (the weight of the end node) and the path
        return end.weight, path

    def connected_component(self, id1: int) -> list:
        """
        SCC algorithm for single node
        :param id1: the node
        :return: list of the connected_component that id1 contains in
        """

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
        """
        for each node in the graph use connected_component method
        :return: nested list of all the connected component in the graph
        """

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
        """
        plot the graph using matplotlib
        :return:
        """

        # positions list will contains all the positions of all the nodes in the graph
        positions = {}

        # epsilon for plot texts in the graph
        epsilon = 0.15

        # going throw every node int the graph
        nodes = self.graph.get_all_v()
        for i in nodes.keys():
            # getting node position
            position = nodes[i].get_pos()

            if isinstance(position, str):
                position = position.split(',')
                position = tuple(position)

            # adding the position to positions list
            positions[i] = position

            # get x and y of the node position
            x = float(position[0])
            y = float(position[1])

            # plot the node
            plt.plot(x, y, color='b', marker='o', markersize=10)

            # each node print near to it the node_id key
            plt.text(x + epsilon, y + epsilon, i, fontsize=10, color='g')

        # for each node print arrow to child
        for i in nodes.keys():

            # get node childes
            childes = self.graph.all_out_edges_of_node(i)

            # go throw node childes
            for j in childes.keys():
                # get source and destination positions
                source = positions[i]
                destination = positions[j]

                source_current_x = float(source[0])
                source_current_y = float(source[1])
                source_current_z = float(source[2])

                destination_current_x = float(destination[0])
                destination_current_y = float(destination[1])
                destination_current_z = float(destination[2])

                p1 = [source_current_x, source_current_y, source_current_z]
                p2 = [destination_current_x, destination_current_y, destination_current_z]

                weight = math.dist(p1, p2)
                head_width = 0.01 * weight * 5
                width = 0.001

                if weight < 1:
                    head_width = 0.0005
                    width = 0.00005

                head_length = head_width

                # print arrow between source and destination nodes
                plt.arrow(float(source[0]), float(source[1]), float(destination[0]) - float(source[0]),
                          float(destination[1]) - float(source[1]),
                          lw=1, length_includes_head=True, head_width=head_width, head_length=head_length,
                          shape='full', width=width)

                weight_x = (float(source[0]) + float(destination[0])) / 2 + epsilon
                weight_y = (float(source[1]) + float(destination[1])) / 2 + epsilon

                # near each arrow print the weight of the edge
                plt.text(weight_x, weight_y, '%.2f' % childes[j], color='r')

        # tight edge of the board to plot nicer
        plt.tight_layout()

        # show board
        plt.show()
