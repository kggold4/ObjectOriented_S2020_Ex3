from unittest import TestCase
from src.DiGraph import NodeData


class TestNodeData(TestCase):

    def test_add_parent(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be parents of n0
        n0.add_parent(n1, 5.5)
        n0.add_parent(n2, 2.5)

        # getting n0 parents (only keys)
        parents = n0.get_parents().keys()

        # check if 01 and n2 is in it
        assert n1 in parents
        assert n2 in parents

    def test_del_parent(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be parents of n0
        n0.add_parent(n1, 5.5)
        n0.add_parent(n2, 2.5)

        # getting n0 parents (only keys)
        parents = n0.get_parents().keys()

        # check if 01 and n2 is in it
        assert n1 in parents
        assert n2 in parents

        # delete n1 and n2 as parents of n0
        n0.del_parent(n1)
        n0.del_parent(n2)

        # getting n0 parents (only keys)
        parents = n0.get_parents().keys()

        # check if n1 and n2 is not in it
        assert n1 not in parents
        assert n2 not in parents

    def test_get_parents(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be parents of n0
        n0.add_parent(n1, 5.5)
        n0.add_parent(n2, 2.5)

        #  getting n0 parents dictionary
        parents = n0.get_parents()

        # getting n0 parents (only keys)
        parents_keys = parents.keys()

        # check if 01 and n2 is in it
        assert n1 in parents_keys
        assert n2 in parents_keys

        # check if return dict type object
        assert isinstance(parents, dict)

    def test_add_child(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be childes of n0
        n0.add_child(n1, 5.5)
        n0.add_child(n2, 2.5)

        # getting n0 childes (only keys)
        childes = n0.get_childes().keys()

        # check if 01 and n2 is in it
        assert n1 in childes
        assert n2 in childes

    def test_del_child(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be childes of n0
        n0.add_child(n1, 5.5)
        n0.add_child(n2, 2.5)

        # getting n0 childes (only keys)
        childes = n0.get_childes().keys()

        # check if 01 and n2 is in it
        assert n1 in childes
        assert n2 in childes

        # delete n1 and n2 as childes of n0
        n0.del_child(n1)
        n0.del_child(n2)

        # getting n0 childes (only keys)
        childes = n0.get_childes().keys()

        # check if n1 and n2 is not in it
        assert n1 not in childes
        assert n2 not in childes

    def test_get_childes(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be childes of n0
        n0.add_child(n1, 5.5)
        n0.add_child(n2, 2.5)

        #  getting n0 childes dictionary
        childes = n0.get_childes()

        # getting n0 childes (only keys)
        childes_keys = childes.keys()

        # check if n1 and n2 is not in it
        assert n1 in childes_keys
        assert n2 in childes_keys

        # check if return dict type object
        assert isinstance(childes, dict)

    def test_is_connected(self):
        # create 3 nodes
        n0 = NodeData(0)
        n1 = NodeData(1)
        n2 = NodeData(2)

        # make n1 and n2 be childes of n0
        n0.add_child(n1, 5.5)
        n0.add_child(n2, 2.5)

        # checking if n0 is connected to n1 and n2
        assert n0.is_connected(n1)
        assert n0.is_connected(n2)