from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphInterface import GraphInterface
from src.DiGraph import DiGraph, NodeData
from typing import List
import matplotlib.pyplot as plt
import numpy as np
import queue
import json


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
        pq = queue.PriorityQueue()

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

    # def connected_component(self, id1: int) -> list:

    #     low = {}
    #     nodes = self.graph.get_all_v()
    #     for node in nodes:
    #         nodes[node].tag = - 1
    #         low[node] = 0
    #
    #     for i in nodes.keys():
    #         if nodes[i].tag == - 1:
    #             self.dfs(id1, low)
    #
    #     return low
    #
    # def dfs(self, i: int, low: dict):
    #     print('new i:', i)
    #     print('low:', low)
    #     nodes = self.graph.get_all_v()
    #     count = 0
    #     stack = [i]
    #     nodes[i].tag += 1
    #     low[i] += nodes[i].tag
    #     for to in self.graph.all_out_edges_of_node(i):
    #         if nodes[to].tag == -1:
    #             print('to:', to)
    #             self.dfs(to, low)
    #         if to not in stack:
    #             print('found to on stack:', to)
    #             low[i] = np.minimum(low[i], low[to])
    #
    #     if nodes[i].tag == low[i]:
    #         while len(stack) != 0:
    #             node = stack.pop()
    #             # low[node] = nodes[i].tag
    #
    #             if node == i:
    #                 break
    #         count += 1
    #         print(count)

        # # nodes dictionary of the graph
        # nodes = self.graph.get_all_v()
        #
        # # reset all tags in the graph to 0
        # for node in nodes:
        #     node.tag = 0
        #
        # # for each node save the previous node
        # previous = {}
        #
        # # using priority queue for the dfs algorithm
        # pq = queue.PriorityQueue()
        #
        # # add id1 to pq
        # pq.put(id1)
        #
        # # set tag of id1 to 1
        # nodes[id1].tag = 1
        #
        # # create a stack for the current connected component and add id1 to the stacks
        # stack = [id1]
        #
        # # using dfs algorithm
        # while not pq.empty():
        #     current_node = pq.get()
        #     for ni in self.graph.all_out_edges_of_node(current_node):
        #         if ni not in stack:
        #             pq.put(ni)
        #             stack.append(ni)
        #             previous[ni] = current_node
        #         else:
        #              nodes[ni].tag = 1
        #
        # current_node = id1
        #
        # while True:
        #     for ni in self.graph.all_out_edges_of_node(current_node):
        #         if ni not in stack:
        #             stack.append(ni)
        #             current_node = ni

    def connected_components(self) -> List[list]:
        nodes = self.graph.get_all_v()
        index = 0
        stack = []
        indexes = {}
        low_link = {}
        on_stack = {}
        for node in nodes.values():
            node.tag = 0

        final_list = []
        for node in nodes.values():
            if node.tag == 0:
                final_list.append(self.strong_connect(node.key, index, stack, indexes, low_link, on_stack))

        return final_list

    def strong_connect(self, v: int, index: int, stack: list, indexes: dict, low_link: dict, on_stack: list):
        nodes = self.graph.get_all_v()

        indexes[v] = index
        low_link[v] = index
        index += 1
        stack.append(v)
        on_stack[v] = True
        for w in self.graph.all_out_edges_of_node(v).keys():
            if nodes[w].tag == 0:
                self.strong_connect(w, index, stack, indexes, low_link, on_stack)
                x = min(low_link[v], low_link[w])
                low_link[v] = float(x)
            elif on_stack[w]:
                low_link[v] = np.minimum(low_link[v], w)

        sc = []
        if low_link[v] == v:

            while w is not v:
                w = stack.pop()
                on_stack[w] = False
                sc.append(w)

        return sc


        #         // If v is a root node, pop the stack and generate an SCC
        #     #         if v.lowlink = v.index then
        #     #             start a new strongly connected component
        #     #             repeat
        #     #                 w := S.pop()
        #     #                 w.onStack := false
        #     #                 add w to current strongly connected component
        #     #             while w ≠ v
        #     #             output the current strongly connected component
        #     #         end if
        #     #     end function





        # algorithm tarjan is
    #     input: graph G = (V, E)
    #     output: set of strongly connected components (sets of vertices)
    #
    #     index := 0
    #     S := empty stack
    #     for each v in V do
    #         if v.index is undefined then
    #             strongconnect(v)
    #         end if
    #     end for
    #
    #     function strongconnect(v)
    #         // Set the depth index for v to the smallest unused index
    #         v.index := index
    #         v.lowlink := index
    #         index := index + 1
    #         S.push(v)
    #         v.onStack := true
    #
    #         // Consider successors of v
    #         for each (v, w) in E do
    #             if w.index is undefined then
    #                 // Successor w has not yet been visited; recurse on it
    #                 strongconnect(w)
    #                 v.lowlink := min(v.lowlink, w.lowlink)
    #             else if w.onStack then
    #                 // Successor w is in stack S and hence in the current SCC
    #                 // If w is not on stack, then (v, w) is an edge pointing to an SCC already found and must be ignored
    #                 // Note: The next line may look odd - but is correct.
    #                 // It says w.index not w.lowlink; that is deliberate and from the original paper
    #                 v.lowlink := min(v.lowlink, w.index)
    #             end if
    #         end for
    #
    #         // If v is a root node, pop the stack and generate an SCC
    #         if v.lowlink = v.index then
    #             start a new strongly connected component
    #             repeat
    #                 w := S.pop()
    #                 w.onStack := false
    #                 add w to current strongly connected component
    #             while w ≠ v
    #             output the current strongly connected component
    #         end if
    #     end function

        # main_list = []
        # low = {}
        # nodes = self.graph.get_all_v()
        # for node in nodes:
        #     nodes[node].tag = - 1
        #     low[node] = 0
        #
        # for i in nodes.keys():
        #     if nodes[i].tag == - 1:
        #         self.dfs(i, low)
        #
        # print('low:', low)

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
