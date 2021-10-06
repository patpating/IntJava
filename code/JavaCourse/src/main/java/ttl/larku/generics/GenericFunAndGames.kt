package ttl.larku.generics

/**
 * @author whynot
 */
class MyThing<T>(val t: T) {

    fun <R> map(mapper: (T) -> R): MyThing<R> {
        val r: R = mapper(t)
        return MyThing(r)
    }
}

fun go() {

    val numberThing: MyThing<Number> = MyThing(22.3)
    val integerThing: MyThing<Int> = MyThing(22)
    //The types are the important things to keep track of
//in this examples.  We say a function maps from the
//first type (the type of the argument) to the second type
//(the return type).  The names of the functions reflect
//the mappings  e.g from Number to Number
    val numberToNumber: (Number) -> Number = { n -> 2.3 }
    val integerToNumber: (Int) -> Number = { n -> 2 }
    val numberToInteger: (Number) -> Int = { n -> 2 }
    val integerToInteger: (Int) -> Int = { n -> 2 }
    //This is straight ahead T's and R's, so it
//would work without the ? super T or ? extends R
    val nt1: MyThing<Number> = numberThing.map(numberToNumber)
    val it1: MyThing<Int> = integerThing.map(integerToInteger)
    //This would not work in Java without the ? super T.
    //Works fine in Kotlin
    val it2: MyThing<Int> = integerThing.map(numberToInteger)
    //This one won't work without the ? extends R in Java.
    //In Kotlin all is good
    val nt3:MyThing<Number> = numberThing.map(numberToInteger)
    //Same as above, but this one *will* work without
    //ditto
    numberThing.map(numberToInteger)
    //We could make it break by being explicit
    //Not in Kotlin
    numberThing.map<Number>(numberToInteger)


    //Using method references

    val nt4: MyThing<Int> = numberThing.map(::fun2)
    //This next one will not compile.  Our T is Number and
    //we are trying to call a function which takes an Integer,
    // i.e. a ? extends Number.  Which is a no no no no no no.
    //val ntBad: MyThing<Number> = numberThing.map(::fun3); //<-- will not compile

    val s = numberThing.map(::fun4)
    val s3: MyThing<Number> = integerThing.map(::fun3)
}

fun fun0(o: Any?): Number {
    return 10
}

fun fun1(n: Number): Number {
    return n.toInt() * n.toInt()
}
fun fun2(n: Number): Int {
    return n.toInt() * n.toInt()
}

fun fun3(n: Int): Int {
    return n * n
}

fun fun4(n: Number): String {
    return "" + n.toInt() * n.toInt()
}



open class Child(val id: Int)
open class Parent(id: Int) : Child(id)
class GrandParent(id: Int) : Parent(id)

fun go2() {
    val childThing = MyThing(Child(1))
    val parentThing = MyThing(Parent(2))

    val childToParent: (Child) -> Parent = {
        c -> Parent(c.id + 1)
    }

    val parentToChild: (Parent) -> Child = {
        p -> Child(p.id + 1)
    }

    val p2: MyThing<Parent> = parentThing.map(childToParent)
    println("${p2.t.id}")

    val p3: MyThing<Child> = parentThing.map(childToParent)
    val p4: MyThing<Child> = MyThing(Parent(20))
//    val p5: MyThing<GrandParent> = MyThing(Parent(20))
}

fun main() {
    go2()
}
