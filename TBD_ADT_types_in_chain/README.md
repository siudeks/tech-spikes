# ADT types and strategies

How to article: https://www.infoq.com/guidelines/


- Introduce ADT types
- Exhaustive pattern matching with 'eval' function
- Comparison with 'Either' type
- comparison with Null - patern 
- usage of composition (instead of field inheritance)

- Introduce strategy design pattern
- Demo how to create chain of processors with different strategy of flow (e.g. all succeded, first failer etc)


private boolean isEmployee; // when true basket id should be populated


----
ShockGroup shockGroup;
if (measure.isBasket()) {
    if (basket == null) {
        throw new NotFoundExcepytion("proper message ...|)
    }
    shockGroup = shockFactory.createShockGroup( ... )
} else {
    shockGroup = shockFactory.createShockGroup2( ... )
}
return shockGroup

... a tutaj example z eval


---

measure.setBasketId(leg.getBasketId() == null ? null : leg.getBasketId().longValue(()))
measure.setIsBasket(leg.isBasket != null);

v2:
measuire.setBasketOption(Optional.ofNullable(leg.isBasket)).map(x -> (BasketOption) new BasketOption.Equiaty(x).orElse(BasketOption.None.INSTANCE)

----

Pokazać nieco rozwiązły kod w mapowaniu algebraic type -> flat type


void setAndVerifyBasketValue(Measure measure, Long basketId, boolean isBasket) {
    if (isBasket) {
        measure.setIsBasket(true)
        if (basketId == null) {
            throw new ExceptionA(...)
        } else if (basketId < 1) {
            throw new ExceptionB(...)
        }
        measure.setBasketId(basketId)
    } else {
        measure.setIsBasket(false)
        measure.setBasketId(null);
    }
}
