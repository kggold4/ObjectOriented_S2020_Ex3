from src.GraphAlgo import GraphAlgo
import networkx as nx
import matplotlib.pyplot as plt
from networkx.readwrite import json_graph
import time
import json


def current_time() -> int:
    """
    get current time in millisecond
    :return:
    """
    return int(round(time.time() * 1000))


def execute(file_name):
    """
    execute time test (in millisecond) with GraphAlgo and NetworkX classes by file_name json of a graph
    :param file_name:
    :return:
    """
    graphAlgo = GraphAlgo()

    print(file_name)

    # graphAlgo load graph
    millis_at_start = current_time()
    graphAlgo.load_from_json(file_name)
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    # networkX load graph
    millis_at_start = current_time()
    network = nx.DiGraph()
    network.add_nodes_from(graphAlgo.get_graph().get_all_v().keys())
    for i in graphAlgo.get_graph().get_all_v().keys():
        childes = graphAlgo.get_graph().all_out_edges_of_node(i)
        for k, v in childes.items():
            network.add_edge(i, k, weight=float(v))
    millis_at_end = current_time()
    print('X - load:', millis_at_end - millis_at_start, 'ms')

    # graphAlgo shortest path
    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    # networkX shortest path
    millis_at_start = current_time()
    try:
        nx.shortest_path(network, source=0, target=9, method='dijkstra')
    except nx.NodeNotFound:
        print('path not found!')
    millis_at_end = current_time()
    print('X - shortest_path:', millis_at_end - millis_at_start, 'ms')

    # graphAlgo connected component
    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    # networkX connected component
    millis_at_start = current_time()
    components = nx.strongly_connected_components(network)
    for i in components:
        if 0 in i:
            break
    millis_at_end = current_time()
    print('X - connected_component:', millis_at_end - millis_at_start, 'ms')

    # graphAlgo connected components
    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_components:', millis_at_end - millis_at_start, 'ms')

    # networkX connected components
    millis_at_start = current_time()
    nx.strongly_connected_components(network)
    millis_at_end = current_time()
    print('X - connected_component:', millis_at_end - millis_at_start, 'ms')


if __name__ == '__main__':

    # paths
    graphs_no_pos = 'data/graphs_no_pos/'
    graphs_on_circle = 'data/graphs_on_circle/'
    graphs_random_pos = 'data/graphs_random_pos/'

    # seeds
    zero = '0'
    one = '1'
    two = '2'

    # execute check
    execute(graphs_no_pos + 'G_10_80_' + zero + '.json')
    execute(graphs_no_pos + 'G_100_800_' + zero + '.json')
    execute(graphs_no_pos + 'G_1000_8000_' + zero + '.json')
    execute(graphs_no_pos + 'G_10000_80000_' + zero + '.json')
    execute(graphs_no_pos + 'G_20000_160000_' + zero + '.json')
    execute(graphs_no_pos + 'G_30000_240000_' + zero + '.json')
