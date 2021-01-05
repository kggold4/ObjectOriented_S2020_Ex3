from unittest import TestCase
from src.GraphCreator import GraphCreator
from src.GraphAlgo import GraphAlgo

size, edges, seed = 25, 20, 0


class TestGraphAlgo(TestCase):
    def test_get_graph(self):
        graph = GraphCreator.create_graph(size, edges, seed)
        graphAlgo = GraphAlgo(graph)
        assert graph is graphAlgo.get_graph()

    def test_save_to_load_from_json(self):
        graph = GraphCreator.create_graph(size, edges, seed)
        graphAlgo1 = GraphAlgo(graph)
        graphAlgo1.save_to_json('graph.json')
        graphAlgo2 = GraphAlgo()
        graphAlgo2.load_from_json('graph.json')
        print('graph 1 is {}'.format(graphAlgo1.get_graph()))
        print('graph 2 is {}'.format(graphAlgo2.get_graph()))
        graphAlgo1.plot_graph()
        # assert graphAlgo1.get_graph.v_size() is graphAlgo2.get_graph.v_size()
        # assert graphAlgo1.get_graph.e_size() is graphAlgo2.get_graph.e_size()

    def test_shortest_path(self):
        graph = GraphCreator.create_graph(size, edges, seed)
        graphAlgo = GraphAlgo(graph)
        print(graphAlgo.shortest_path(0,10))

    # def test_connected_component(self):
        # self.fail()

    # def test_connected_components(self):
        # self.fail()

    # def test_plot_graph(self):
        # self.fail()
