import json

import numpy as np


class Monomer:
    def __init__(self, lower_right_occupancy):
        self.lower_right_occupancy = np.asarray(lower_right_occupancy)
        self.bonds = []

    OFFSETS = np.array([
        [0, 0],
        [0, 1],
        [1, 0],
        [1, 1]
    ])

    def get_occupation_sites(self):
        return self.OFFSETS + self.lower_right_occupancy


class Bond:
    def __init__(self, a: Monomer, b: Monomer):
        self.a = a
        self.b = b
        a.bonds.append(self)
        b.bonds.append(self)


class Polymer:
    def __init__(self, monomers=()):
        self.monomers = list(monomers)

    def to_json(self):
        monomer_locations = [(i, list(map(int, monomer.lower_right_occupancy)))
                             for i, monomer in enumerate(self.monomers)]
        monomer_to_index = {monomer: i for i, monomer in enumerate(self.monomers)}
        bonds = {b for monomer in self.monomers for b in monomer.bonds}
        bond_indices = [(monomer_to_index[b.a], monomer_to_index[b.b]) for b in bonds]
        return {'monomer_locations': monomer_locations,
                'bond_indices': bond_indices}

    @classmethod
    def from_json(cls, obj: dict):
        indexed_monomers = {index: Monomer((x, y)) for index, (x, y) in obj['monomer_locations']}
        for i, j in obj['bond_indices']:
            Bond(indexed_monomers[i], indexed_monomers[j])
        return cls(indexed_monomers.values())


class Occlusion:
    def __init__(self, sites):
        sites = np.asarray(sites, dtype=int)
        assert len(sites.shape) == 2
        self.sites = sites

    def to_json(self):
        return {'sites': [list(map(int, site)) for site in self.sites]}

    @classmethod
    def from_json(cls, obj: dict):
        return cls(obj['sites'])


class Configuration:
    def __init__(self, width, height, polymers=(), occlusions=(), steps=None):
        self.width = width
        self.height = height
        self.polymers = list(polymers)
        self.occlusions = list(occlusions)
        self.steps = steps

    def to_json(self):
        return {'width': self.width,
                'height': self.height,
                'polymers': [p.to_json() for p in self.polymers],
                'occlusions': [o.to_json() for o in self.occlusions]}

    @classmethod
    def from_json(cls, obj: dict):
        return cls(width=obj['width'], height=obj['height'],
                   polymers=[Polymer.from_json(p) for p in obj['polymers']],
                   occlusions=[Occlusion.from_json(o) for o in obj['occlusions']],
                   steps=obj.get('steps', None))
