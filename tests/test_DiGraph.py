from unittest import TestCase
from src.DiGraph import DiGraph
from src.GraphCreator import GraphCreator

size = 25


class TestDiGraph(TestCase):
    def test_v_size(self):
        global size
        graph = GraphCreator.create_graph(size)
        assert size == graph.v_size()

    def test_e_size(self):
        global size
        graph = GraphCreator.create_graph(size)
        for i in range(size):
            if i != 0:
                graph.add_edge(0, i, 10)

        # size - 2 = graph.e_size
        # because not included node 0 and node with 'size' id (aka node 10000)
        assert size - 1 == graph.e_size()
        for i in range(size):
            if i != 0:
                graph.remove_edge(0, i)
        assert 0 == graph.e_size()

        for i in range(size):
            if i != 0:
                graph.add_edge(0, i, 10)
        assert size - 1 == graph.e_size()

        temp_size = size
        for i in range(temp_size):
            graph.remove_node(i)
        assert 0 == graph.e_size()

        graph = GraphCreator.create_graph(size, 10)
        assert 10 == graph.e_size()

    def test_get_all_v(self):
        global size
        graph = GraphCreator.create_graph(size)
        all_v = graph.get_all_v()
        assert size == len(all_v)
        assert isinstance(all_v, dict)
        for i in range(size - 1):
            assert i in all_v.keys()

    def test_all_in_edges_of_node(self):
        global size
        same_seed = 1
        graph = GraphCreator.create_graph(size, 10, same_seed)
        parents = graph.all_in_edges_of_node(0)
        assert 15 in parents.keys()
        parents = graph.all_in_edges_of_node(2)
        assert 7 in parents
        parents = graph.all_in_edges_of_node(11)
        assert 9 in parents
        parents = graph.all_in_edges_of_node(12)
        assert 4 in parents
        parents = graph.all_in_edges_of_node(15)
        assert 1 in parents
        parents = graph.all_in_edges_of_node(17)
        assert 16 in parents
        parents = graph.all_in_edges_of_node(18)
        assert 2 in parents
        parents = graph.all_in_edges_of_node(19)
        assert 12 in parents
        parents = graph.all_in_edges_of_node(22)
        assert 6 in parents
        parents = graph.all_in_edges_of_node(24)
        assert 8 in parents

    def test_all_out_edges_of_node(self):
        global size
        same_seed = 1
        graph = GraphCreator.create_graph(size, 10, same_seed)
        childes = graph.all_out_edges_of_node(1)
        assert 15 in childes
        childes = graph.all_out_edges_of_node(2)
        assert 18 in childes
        childes = graph.all_out_edges_of_node(4)
        assert 12 in childes
        childes = graph.all_out_edges_of_node(6)
        assert 22 in childes
        childes = graph.all_out_edges_of_node(7)
        assert 2 in childes
        childes = graph.all_out_edges_of_node(8)
        assert 24 in childes
        childes = graph.all_out_edges_of_node(9)
        assert 11 in childes
        childes = graph.all_out_edges_of_node(12)
        assert 19 in childes
        childes = graph.all_out_edges_of_node(15)
        assert 0 in childes
        childes = graph.all_out_edges_of_node(16)
        assert 17 in childes
        assert isinstance(childes, dict)

    def test_get_mc(self):
        global size
        graph = GraphCreator.create_graph(size * 100, 1000, 10)
        assert 3500 == graph.get_mc()
        graph.remove_node(0)
        assert 3500 < graph.get_mc()
        current_mc = graph.get_mc()
        graph.add_node(0)
        assert current_mc + 1 == graph.get_mc()

    def test_add_edge(self):
        global size
        graph = DiGraph()
        for i in range(5):
            graph.add_node(i)
        for i in range(4):
            graph.add_edge(i, 4, 15)
        for i in range(4):
            assert i in graph.all_in_edges_of_node(4)

    def test_add_node(self):
        global size
        graph = GraphCreator.create_graph(size, size * 2, 5)
        assert size == graph.v_size()

    def test_remove_node(self):
        global size
        graph = DiGraph()
        for i in range(size):
            graph.add_node(i)
        assert size == graph.v_size()
        for i in range(size):
            graph.remove_node(i)
        assert 0 == graph.v_size()

        graph = GraphCreator.create_graph(200, 100, 2)
        assert 100 == graph.e_size()
        assert 200 == graph.v_size()
        for i in range(200):
            graph.remove_node(i)
        assert 0 == graph.e_size()
        assert 0 == graph.v_size()

    def test_remove_edge(self):
        graph = GraphCreator.create_graph()
        for i in range(size):
            graph.add_node(i)
        for i in range(size):
            if i != 0:
                graph.add_edge(i, 0, 4.5)
        assert size - 1 == graph.e_size()
        for i in range(size):
            if i != 0:
                graph.remove_edge(i, 0,)
        assert 0 == graph.e_size()
