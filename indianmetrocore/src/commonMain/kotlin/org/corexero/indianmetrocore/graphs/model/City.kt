package org.corexero.indianmetrocore.graphs.model

enum class City(
    val dbCityId: Long,
    val topologyId: Int?
) {
    Mumbai(
        1,
        14
    ),
    Delhi(
        2,
        null
    ),
    Chennai(
        3,
        4
    ),
    Bengaluru(
        4,
        2
    ),
    Hyderabad(
        5,
        6
    ),
    Kolkata(
        6,
        11
    ),
    Kochi(
        7,
        9
    ),
    Pune(
        8,
        16
    ),
    Lucknow(
        9,
        12
    ),
    Ahmedabad(
        10,
        0
    ),
    Jaipur(
        11,
        7
    ),
    Kanpur(
        12,
        8
    ),
    Nagpur(
        13,
        15
    ),
    Agra(
        14,
        1
    )
}