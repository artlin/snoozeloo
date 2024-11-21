package com.plcoding.snoozeloo.core.data.mapper


abstract class DataMapper<TypeA, TypeB>() {

    @JvmName("convertAtoB")
    suspend fun convert(input: TypeA): TypeB? = fromAtoB(input)

    @JvmName("convertBToA")
    suspend fun convert(input: TypeB): TypeA? = fromBtoA(input)

    @JvmName("listConvertAtoB")
    suspend fun convert(input: List<TypeA>): List<TypeB> = fromAtoB(input)

    @JvmName("listConvertBtoA")
    suspend fun convert(input: List<TypeB>): List<TypeA> = fromBtoA(input)

    protected abstract suspend fun fromAtoB(input: TypeA): TypeB?

    protected abstract suspend fun fromBtoA(input: TypeB): TypeA?

    protected suspend fun fromAtoB(list: List<TypeA>): List<TypeB> =
        list.mapNotNull { fromAtoB(it) }

    protected suspend fun fromBtoA(list: List<TypeB>): List<TypeA> =
        list.mapNotNull { fromBtoA(it) }

}
