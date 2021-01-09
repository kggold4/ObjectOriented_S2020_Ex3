from src.DiGraph import DiGraph
from src.GraphInterface import GraphInterface
from src.GraphCreator import GraphCreator
from src.GraphAlgoInterface import GraphAlgoInterface
from src.GraphAlgo import GraphAlgo
import time


def current_time() -> int:
    return int(round(time.time() * 1000))


if __name__ == '__main__':

    graph = DiGraph()
    graphAlgo = GraphAlgo(graph)

    print('G_10_80_0:')

    millis_at_start = current_time()
    graphAlgo.load_from_json("json_graphs/G_10_80_0.json")
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    print('G_100_800_0:')

    millis_at_start = current_time()
    graphAlgo.load_from_json("json_graphs/G_100_800_0.json")
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    print('G_1000_8000_0:')

    millis_at_start = current_time()
    graphAlgo.load_from_json("json_graphs/G_1000_8000_0.json")
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    print('G_10000_80000_0:')

    millis_at_start = current_time()
    graphAlgo.load_from_json("json_graphs/G_10000_80000_0.json")
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    print('G_20000_160000_0:')

    millis_at_start = current_time()
    graphAlgo.load_from_json("json_graphs/G_20000_160000_0.json")
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    print('G_30000_240000_0:')

    millis_at_start = current_time()
    graphAlgo.load_from_json("json_graphs/G_30000_240000_0.json")
    millis_at_end = current_time()
    print('load:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.shortest_path(0, 9)
    millis_at_end = current_time()
    print('shortest_path:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_component(0)
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')

    millis_at_start = current_time()
    graphAlgo.connected_components()
    millis_at_end = current_time()
    print('connected_component:', millis_at_end - millis_at_start, 'ms')
