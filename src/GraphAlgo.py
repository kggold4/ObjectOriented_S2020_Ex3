from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph, NodeData

# using nested list in SCC algorithm
from typing import List

# using matplotlib to plot the graph
import matplotlib.pyplot as plt

# using priority queue in shortest path algorithm
from queue import PriorityQueue

# using simple queue in SCC algorithm
from queue import SimpleQueue
import json


class GraphAlgo(GraphAlgoInterface):

    index = 0
    count = 0

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
        if self.graph.get_all_v().get(id1) is None:
            return []
            # run bfs on graph with id1 as src we the edges as is
        self.bfs(id1, False)
        help_list = []
        # adds all the edges that are reachable from src to a list
        for node in self.graph.get_all_v().values():
            if node.tag != -1:
                help_list.append(node)
        # run bfs on graph transpose with id1 as src (read the edges upside down)
        self.bfs(id1, True)
        # if they are still reachable after the edges are flipped its means that its SCC
        help_list = [node for node in help_list if node.tag != -1]  # dealt all of the nodes that are not reachable
        for node in help_list:
            node.set_connected_component(id1)  # update their SCC to src
        return help_list

    def connected_components(self) -> List[list]:
        if self.graph.v_size() == 0:
            return []
            # reset all the components
        for node in self.graph.get_all_v().values():
            node.set_connected_component(None)
        my_list = []
        # for each node check if its SCC is None is yes  runs connected_components(node)
        # and its SCC  to the list of SCC
        for node in self.graph.get_all_v().values():
            if node.connected_component is None:
                list_help = self.connected_component(node.key)
                my_list.append(list_help)
        return my_list

        # main_list = []
        #
        # # each node save True if the node have visited, otherwise False
        # visited = {}
        # nodes = self.graph.get_all_v()
        #
        # # for each node tag set the node id and set as not visited
        # for node in nodes.keys():
        #     nodes[node].tag = nodes[node].key
        #     visited[node] = False
        #
        # for node in nodes.keys():
        #     for i in nodes.keys():
        #         nodes[i].tag = nodes[i].key
        #     pq = queue.PriorityQueue()
        #     previous = {}
        #     stack = []
        #     pq.put(node)
        #     stack.append(node)
        #     visited[node] = True
        #     print('start from {}'.format(node))
        #     start = node
        #     while not pq.empty():
        #         pop = pq.get()
        #         print('\tpop node: {}'.format(pop))
        #         for ni in self.graph.all_out_edges_of_node(pop):
        #             print('\t\tgo to ni: {}'.format(ni))
        #             if visited[ni] is False:
        #                 print('\t\t\tnode {} not have been visited'.format(ni))
        #                 stack.append(ni)
        #                 visited[ni] = True
        #                 pq.put(ni)
        #                 previous[ni] = pop
        #             else:
        #                 print('\t\t\tnode {} already have been visited'.format(ni))
        #                 inner_list = []
        #                 target = nodes[ni].tag
        #                 prev = previous[ni]
        #                 inner_list.append(prev)
        #                 while nodes[prev].tag != target:
        #                     print('\t\t\t\tgo previous to {}'.format(prev))
        #                     print('\t\t\t\t\ttarget is {}'.format(target))
        #                     nodes[prev].tag = target
        #                     inner_list.append(prev)
        #                     if start == prev:
        #                         stack.pop()
        #                         break
        #                     prev = previous[prev]
        #                 main_list.append(inner_list)
        #                 print(main_list)
        #
        # print(main_list)

        # self.index = 0
        # self.count = 0
        # stack = []
        # indexes = {}
        # low_link = {}
        # on_stack = {}
        # for node in nodes.values():
        #     node.tag = 0
        #
        # final_list = []
        # for node in nodes.values():
        #     if node.tag == 0:
        #         final_list.append(self.strong_connect(node.key, stack, indexes, low_link, on_stack))
        #
        # for i in nodes.values():
        #     print(i.key, i.tag)
        #     print(i.key, indexes[i.key])
        #
        # return final_list

    # def strong_connect(self, v: int, stack: list, indexes: dict, low_link: dict, on_stack: list):
    #     nodes = self.graph.get_all_v()
    #
    #     indexes[v] = self.index
    #     low_link[v] = self.index
    #     self.index += 1
    #     stack.append(v)
    #     on_stack[v] = True
    #     for w in self.graph.all_out_edges_of_node(v).keys():
    #         if nodes[w].tag == 0:
    #             self.strong_connect(w, stack, indexes, low_link, on_stack)
    #             low_link[v] = min(low_link[v], low_link[w])
    #         elif not on_stack[w]:
    #             continue
    #         else:
    #             low_link[v] = np.minimum(low_link[v], w)
    #
    #     sc = []
    #     if low_link[v] == v:
    #         self.count += 1
    #         while w is not v:
    #             w = stack.pop()
    #             nodes[w].tag = self.count
    #             on_stack[w] = False
    #             sc.append(w)
    #
    #     return sc

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

    def bfs(self, node_key: int, upside_down: bool):
        """
        gets src and runs bfs algorithm on the graph from src
        if upside down is true that run the bfs on graph transpose
        :param node_key:
        :param upside_down:
        :return:
        """
        # initialize all the tags to -1
        for node in self.graph.get_all_v().values():
            node.tag = -1
        queue = SimpleQueue()
        src = self.graph.get_all_v().get(node_key)
        src.tag = 0
        queue.put(src)
        while not queue.empty():
            node_temp = queue.get()
            # graph as is
            if upside_down is False:
                neighbors = self.graph.all_out_edges_of_node(node_temp.key)
            # graph transpose
            else:
                neighbors = self.graph.all_in_edges_of_node(node_temp.key)
            for key in neighbors:
                node_neighbor = self.graph.get_all_v().get(key)
                if node_neighbor.tag == -1:  # the first time this node is reached
                    node_neighbor.tag = node_temp.tag + 1
                    queue.put(node_neighbor)
