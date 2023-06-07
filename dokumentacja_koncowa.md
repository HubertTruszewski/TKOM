# Techniki kompilacji - projekt - dokumentacja końcowa

```
Hubert Truszewski
Nr albumu: 304384
```

## Opis zadania

Język do opisu brył i ich właściwości. Podstawowe bryły(prostopadłościan, ostrosłup, stożek, walec, kula itd.) są wbudowanymi typami języka. Każdy typ posiada wbudowanemetody służące do wyznaczania charakterystycznych dla niego wielkości, np. pole podstawy, pole powierzchni bocznej, objętość, wysokość, średnica itp. Kolekcja brył tworzy scenę wyświetlaną na ekranie.

## Gramatyka języka

```
program                    = {function_declaration};
function_declaration       = type, identifier, "(", [function_parameters] ")", code_block;
function_parameters        = parameter_declaration, {",", parameter_declaration};
parameter_declaration      = type, identifier;
code_block                 = "{", {statement}, "}";
statement                  = conditional_statement
                           | variable_declaration
                           | return_statement
                           | variable_assignment_or_expression;
conditional_statement      = if_statement | while_statement;
if_statement               = "if (", expression, ")", code_block, ["else", code_block];
while_statement            = "while (", expression, ")", code_block;
variable_declaration       = type, identifier, "=", expression ";";
variable_assignment_or_expression = [identifier, "="], expression, ";";
return_statement           = "return", [expression], ";";
expression                 = or_expression;
or_expression              = and_expression, {or_operator, and_expression};
and_expression             = comparison_expression, {and_operator, comparison_expression};
comparison_expression      = addition_expression, {comparison_opearator, addition_expression};
addition_expression        = multiplication_expression, {addition_operator, multiplication_expression};
multiplication_expression  = casting_expression, {multiplication_operator, casting_expression};
casting_expression         = negation_expression, [casting_operator, type];
negation_expression        = [negation_operator | minus_sign], access_expression;
access_expression          = simple_expression, {access_operator, identifier_or_function_call};
simple_expression          = number | string_literal | identifier_or_function_call | "(", expression, ")";
identifier_or_function_call = identifier, [function_call_parameters];
function_call_parameters   = "(", expression, {",", expression}, ")";
type                       = "int"
                           | "string"
                           | "double"
                           | "bool"
                           | "void"
                           | "Cone"
                           | "Cylinder"
                           | "Sphere"
                           | "Cuboid"
                           | "Pyramid"
                           | "List"
                           | "Iterator";
identifier                 = letter, {letter | digit};
string_literal             = '"', {string_element}, '"';
string_element             = letter | escape_character | digit | special_character;
letter                     = "A" | "B" | ... | "Z" | "a" | "b" | ... | "z";
escape_character           = "\";
special_character          = " " | "." | "," | "@" | "!" | "#" | ...
number                     = int_number | float_number;
float_number               = int_number, ".", int_number;
int_number                 = "0" | (digit_non_zero, {digit});
digit                      = "0" | digit;
digit_non_zero             = "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9" |;

addition_operator          = "+" | minus_sign;
multiplication_operator    = "*" | "/";
negation_operator          = "!";
access_operator            = ".";
comparison_operator        = "==", "!=", ">", "<", ">=", "<=";
and_operator               = "&&";
or_operator                = "||";
casting_operator           = "as";
minus_sign                 = "-";

```

## Precedencja operatorów

1 = najwyższy priorytet

| Priorytet | Operator                                       |     Łączność |
| --------- | :--------------------------------------------- | -----------: |
| 1         | Dostęp do obiektu (`.`)                        |  lewostronny |
| 2         | Negacja (`!`)                                  | prawostronny |
| 3         | Rzutowanie (`as`)                              |  lewostronny |
| 4         | Mnożenie oraz dzielenie (`*`, `/`)             |  lewostronny |
| 5         | Dodawanie oraz odejmowanie (`+`, `-`)          |  lewostronny |
| 6         | Porównanie (`==`, `!=`, `>`, `<`, `>=`, `<=`>) |  lewostronny |
| 7         | operacja AND (`&&`)                            |  lewostronny |
| 8         | operacja OR (`&#124;&#124;`)                   |  lewostronny |

## Opis implementacji

Język jest realizowany jako silnie i statycznie typowany. Punktem wejścia do programu jest obowiązowa, bezargumentowa funkcja o nazwie `main`. Brak jej obecności spowoduje błąd i niewykonanie programu. Wyświetlanie elementów na ekranie zrealizowane jest przy pomocy JavaFX.

Projekt składa się z trzech głównych elementów:

-   leksera
-   parsera
-   interpretera

### Lekser (analizator leksykalny)

Zadaniem leksera jest leniwe generowanie tokenów na podstawie znaków pochodzących ze źródła danych, którym może być plik lub inna klasa dziedzicąca po klasie `Reader`.
Zdefiniowanych jest 48 typów tokenów. Token może być jedno lub wieloznakowy.

Tokeny można również podzielić ze względu na przetrzymywaną zawartość:

-   StringToken - przetrzymuje napis, np. Identifier
-   IntegerToken - przetrzymuje wartości typu całkowitego
-   DoubleToken - przetrzumuje numeryczne wartości zmiennoprzecinkowe
-   EmptyToken - nie przetrzymuje żadnej wartości, np. znak równości (=)

Każdy token zawiera również informacje o swojej pozycji w dokumencie.

### Parser

Na podstawie kolejnych tokenów dostarczanych przez lekser parser buduje drzewo rozbioru dokumentu. Zwracanym przez metodę `parse()` typem obiektu jest `Program`, który zawiera definicje funkcji, które z kolei zwierają informacje o argumentach oraz blok z instrukcjami programu. Implementacja zawarta jest w pliku `ParserImpl.java`. Każdy element z gramatyki jest parsowany przez metodę nad którą jest zawary w komentarzu odpowiadający fragment z gramatyki. Każdy element produkowany przez parser implementuje interfejs `Statement`, który jest używany przy odwiedzaniu elementów drzewa AST przy użyciu interpretera.

### Interpreter

Interpreter działa z użyciem wzorca wizytatora, który odwiedza kolejne węzły drzewa wyprodukowanego przez parser. Interpreter posiada również własne funkcje wbudowane, które są dodawane do mapy funckji przy tworzeniu obiektu interpretera.

### Typy danych

Dostępne są następujące typy danych:

-   int - typ stałoprzecinkowy
-   double - typ zmiennoprzecinkowy
-   string - typ danych tekstowych
-   bool - typ danych logiczny
-   void - brak zwracanego typu (używane w funkcjach)
-   Cuboid - prostopadłościan
-   Pyramid - ostrosłup prawidłowy trójkątny
-   Cone - stożek
-   Cylinder - walec
-   Sphere - kula
-   List - lista

### Prostopadłościan

Nazwa: Cuboid

Pola:

-   double a - szerokość
-   double b - długość
-   double H - wysokość

Metody:

-   double volume() - zwraca obliczoną objętość
-   double baseSurface() - zwraca pole podstawy
-   double lateralSurface() - zwraca pole powierzchni bocznej
-   double totalSurface() - zwraca pole powierzchni całkowitej

### Ostrosłup prawidłowy trójkątny

Nazwa: Pyramid

Pola:

-   double a - długość boku podstawy
-   double H - wysokość

Metody:

-   double volume() - zwraca obliczoną objętość
-   double baseSurface() - zwraca pole podstawy
-   double lateralSurface() - zwraca pole powierzchni bocznej
-   double totalSurface() - zwraca pole powierzchni całkowitej

### Stożek

Nazwa: Cone

Pola:

-   double r - długość promienia podstawy
-   double H - wysokość

Metody:

-   double volume() - zwraca obliczoną objętość
-   double baseSurface() - zwraca pole podstawy
-   double lateralSurface() - zwraca pole powierzchni bocznej
-   double totalSurface() - zwraca pole powierzchni całkowitej

### Walec

Nazwa: Cylinder

Pola:

-   double r - długość promienia podstawy
-   double H - wysokość

Metody:

-   double volume() - zwraca obliczoną objętość
-   double baseSurface() - zwraca pole podstawy
-   double lateralSurface() - zwraca pole powierzchni bocznej
-   double totalSurface() - zwraca pole powierzchni całkowitej

### Kula

Nazwa: Sphere

Pola:

-   double R - długość promienia kuli

Metody:

-   double volume() - zwraca obliczoną objętość
-   double totalSurface() - zwraca pole powierzchni całkowitej
-   double diameter() - zwraca długość średnicy

### List

Nazwa: List

Metody:

-   void add(Figure) - dodaje figurę do kolekcji
-   Iterator iterator() - zwraca iterator

### Iterator

Nazwa: Iterator

Metody:

-   bool hasNext() - zwraca informacje czy iterator ma dostępny kolejny element
-   Iterator iterator() - zwraca kolejną wartość z listy

### Funkcje wbudowane

-   print(string) - wypisuje do strumienia napis podany jako argument
-   newCuboid(double, double, double) - tworzy nowy obiekt prostopadłościanu
-   newPyramid(double, double) - tworzy nowy obiekt ostrosłupa trójkątnego prawidłowego
-   newCone(double, double) - tworzy nowy obiekt stożka
-   newCylinder(double, double) - tworzy nowy obiekt walca
-   newSpehere(double, double) - tworzy nowy obiekt kuli
-   newList() - tworzy nowy obiekt listy
-   showFigures(List) - otwiera okno i wyświetla bryły z kolekcji przekazanej jako argument

### Przykładowy kod programu

```
void main() {
    Sphere c = newSphere(15.0);
    List l = newList();
    l.add(c);
    Cuboid d = newCuboid(30.0, 20.0, 50.0);
    l.add(d);
    showFigures(l);
}
```

### Opis użytkowy

Podstawowymi składowymi programu są definicje funkcji. Program musi mieć zdefiniowaną funkcję `main()`, od której rozpoczyna się wykonywanie kodu.

Dla użytkownika dostępne są: instrukcja warunkowa `if` oraz pętla `while`.

Użytkownik ma możliwość defninowania komentarzy poprzed umieszczenie `//` na początku linijki.
Operacje dodawania, odejmowania, mnożenia i dzielenia są zdefiniowane dla typów `int` i `double`.
Aby je wykonować muszą być tego samego typu. Podobnie operacje porównania również działają na typach liczbowych.

Rzutowanie można wykonać z double na int, z int na double oraz z int, bool, double na string.

### Testowanie

Do testowania używana jest JUnit.

Dla leksera zostały przygotowane testy jednostkowe, które sprawdzają poprawność budowania każdego ze zdefiniowanych tokenów. Ponadto zostały uwzględnione sytuacje błędne jak np. zbyt duża liczba dla int.

Parser został przetestowany za pomocą testów jednostkowych i integracyjnych. Testy jednostkowe polegały na zastosowaniu zamockowanego leksera, który zwracał kolejne tokeny z listy przekazanej przy tworzeniu. Testy itegracyjne pobierały znaki ze źródła plikowego i sprawdzały poprawność zbudowanego drzewa dokumentu.

Intepreter został przetestowany przy pomocy testów jednostkowych, które sprawdzały poprawność działania funkcji wbudowanych. Ponadto ręcznie zostały przetestowane różne konstrukcje językowe i zweryfikowane zostało ich poprawne wykonanie.
