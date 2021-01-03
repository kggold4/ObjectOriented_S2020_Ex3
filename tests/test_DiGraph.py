from unittest import TestCase
from src import DiGraph
import random

size = 25


def create_graph(n: int = size, seed_num: int = 0) -> DiGraph:
    random.seed(seed_num)
    pos = []

    for i in range(n*2):
        pos.append(random.random() * 10)

    graph = DiGraph.DiGraph()

    for i in range(n - 1):
        if i % 2 == 0:
            p = (pos[i], pos[i + 1])
        graph.add_node(i, p)

    return graph


class TestDiGraph(TestCase):
    def test_v_size(self):
        global size
        graph = create_graph(size)
        assert size == graph.v_size()

    def test_e_size(self):
        global size
        graph = create_graph(size)
        for i in range(size - 1):
            if i != 0:
                graph.add_edge(0, i, 10)

        # size - 2 = graph.e_size
        # because not included node 0 and node with 'size' id (aka node 10000)
        assert size - 2 == graph.e_size()
        for i in range(size - 1):
            if i != 0:
                graph.remove_edge(0, i)
        assert 0 == graph.e_size()

        for i in range(size - 1):
            if i != 0:
                graph.add_edge(0, i, 10)
        assert size - 2 == graph.e_size()

        temp_size = size
        for i in range(temp_size):
            graph.remove_node(i)
        assert 0 == graph.e_size()

    def test_get_all_v(self):
        graph = create_graph(size)
        all_v = graph.get_all_v()
        assert size == len(all_v)
        assert isinstance(all_v, dict)
        for i in range(size - 1):
            assert i in all_v.keys()

    # def test_all_in_edges_of_node(self):
    # def test_all_out_edges_of_node(self):
    # def test_get_mc(self):
    # def test_add_edge(self):
    # def test_add_node(self):
    # def test_remove_node(self):
    # def test_remove_edge(self):
