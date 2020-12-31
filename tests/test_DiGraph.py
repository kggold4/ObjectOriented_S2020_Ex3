from unittest import TestCase
from src import DiGraph


def create_graph(n: int = 10) -> DiGraph:
    graph = DiGraph()
    for i in range(n):
        graph.add_node(i)
    return graph


class TestDiGraph(TestCase):
    def test_v_size(self):
        size = 100000
        graph = create_graph(size)
        assert size == graph.v_size()

    #
    # def test_e_size(self):
    # def test_get_all_v(self):
    # def test_all_in_edges_of_node(self):
    # def test_all_out_edges_of_node(self):
    # def test_get_mc(self):
    # def test_add_edge(self):
    # def test_add_node(self):
    # def test_remove_node(self):
    # def test_remove_edge(self):