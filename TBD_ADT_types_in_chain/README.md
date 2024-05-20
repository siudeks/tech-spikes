How to article: https://www.infoq.com/guidelines/

# ADT types and strategies

Poznaj nowego przyjaciela: Typy Algebraiczne. Niech Cię nie zwiedzie surowość tej nazwy, drzemie tam deszcz obietnic

Nasz Nowy Cudowny Serwis przetwarza obiekt wejściowy klasy Request na 

## Pola Schrödingera


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



class InterpolationCorategy {

    StrategyType: enum // w zaleznosci od enum pola poniżej są wypełniane
    field1Name
    field1Unit
    field2Name
    field2Unit

}




innym przykłądem jest przenoszenie obiektu prezz flow, gdzie jest on swojego rodzaju builderem, zaś niewiedza oa ym pozwala odczytywać jego nieprzetwaorzone części bez wiedzy ze to nie jest finalna postać


Example:
Preconditions,checkArgument(group.isValid())



public boolean isValid() {
    return allocationResult != null && allocationResult.getMappingAllocationResult() != null && allocationResult.getMappingAllocationResult().isValue()
}


Inny przykład: stosowanie ścieżki do danych
jeżeli DATA_LOCATION = "ABC" użyj provider Abc
w innym przypadku oznacza to fizyczną lokację danych i należy je po prostu wczytać

Kiedy 'specjalna' opcja jest null-em ale ktośbędzie próbowałstworzyć mapę, wtedy Null wywala stolik bo nie może być użyty



Potem powstają metody z podobnymi nazwami
whenValieType(...)
whenInvalidType(...)


po wprowadzeniu metody isvalid, ponieważ jest ona metodą z logikąw środku, wymaga ona
- utrzymania
- mockowanie (ponieważ jest tylko metodą read only)