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
        print('graphAlgo1 {}'.format(graphAlgo1.get_graph()))
        print('graphAlgo2 {}'.format(graphAlgo2.get_graph()))
        print('graphAlgo1 e size {}'.format(graphAlgo1.get_graph().e_size()))
        print('graphAlgo2 e size {}'.format(graphAlgo2.get_graph().e_size()))
        print('graphAlgo1 v size {}'.format(graphAlgo1.get_graph().v_size()))
        print('graphAlgo2 v size {}'.format(graphAlgo2.get_graph().v_size()))
        for i in range(graphAlgo1.get_graph().v_size()):
            print('graphAlgo1 childes {}'.format(graphAlgo1.get_graph().all_out_edges_of_node(i)))
            print('graphAlgo2 childes {}'.format(graphAlgo2.get_graph().all_out_edges_of_node(i)))

        # print('graph 1 is {}'.format(graphAlgo1.get_graph()))
        # print('graph 2 is {}'.format(graphAlgo2.get_graph()))
        # nodes1 = graphAlgo1.get_graph().get_all_v()
        # nodes2 = graphAlgo1.get_graph().get_all_v()
        # assert nodes1 is nodes2
        #
        # for i in nodes1.keys():
        #     childes1 = graphAlgo1.get_graph().all_out_edges_of_node(i)
        #     childes2 = graphAlgo2.get_graph().all_out_edges_of_node(i)
        #     print('childes1:', childes1)
        #     print('childes2:', childes2)
        #     assert childes1 == childes2
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
