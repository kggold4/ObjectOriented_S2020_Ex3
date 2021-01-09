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

        # create randomly graph
        graph = GraphCreator.create_graph(size, edges, seed)
        graphAlgo1 = GraphAlgo(graph)

        # save the graph to a json file
        graphAlgo1.save_to_json('graph.json')

        # create another graph
        graphAlgo2 = GraphAlgo()

        # load the graph from a file
        graphAlgo2.load_from_json('graph.json')

        # check of the graphs have the same nodes
        for i in graph.get_all_v().keys():
            assert graphAlgo1.get_graph().get_all_v().get(i).key is graphAlgo2.get_graph().get_all_v().get(i).key

        # same mc
        assert graphAlgo1.get_graph().get_mc() is graphAlgo2.get_graph().get_mc()

        # same edge size
        assert graphAlgo1.get_graph().e_size() is graphAlgo2.get_graph().e_size()

        # same node size
        assert graphAlgo1.get_graph().v_size() is graphAlgo2.get_graph().v_size()

        # same chides and parent
        for i in graph.get_all_v().keys():
            childes1 = graphAlgo1.get_graph().all_out_edges_of_node(i)
            childes2 = graphAlgo2.get_graph().all_out_edges_of_node(i)
            parents1 = graphAlgo1.get_graph().all_in_edges_of_node(i)
            parents2 = graphAlgo2.get_graph().all_in_edges_of_node(i)

            for j in childes1.keys():
                assert childes1[j] == childes2[j]
            for j in parents1.keys():
                assert parents1[j] == parents2[j]

    def test_shortest_path(self):
        graph = GraphCreator.create_graph(10, 40, 5)
        graphAlgo = GraphAlgo(graph)
        sp = graphAlgo.shortest_path(6, 8)
        distance = 15.385325889119228
        path = [6, 5, 9, 8]
        print(sp[0])
        print(distance)
        print(sp[1])
        assert float(sp[0]) == float(distance)
        j = 0
        for i in sp[1]:
            # assert i == path[j]
            print(i)
            print(path[j])
            print(type(i))
            print(type(path[j]))
            j += 1

    # def test_connected_component(self):
        # self.fail()

    # def test_connected_components(self):
        # self.fail()

    # def test_plot_graph(self):
        # self.fail()
