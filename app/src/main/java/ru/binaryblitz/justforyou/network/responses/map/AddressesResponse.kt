package ru.binaryblitz.justforyou.network.responses.map

data class AddressesResponse(
	val results: List<ResultsItem?>? = null,
	val status: String? = null
)
