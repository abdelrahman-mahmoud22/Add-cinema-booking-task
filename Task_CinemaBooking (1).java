import java.util.*;

/*
 =======================================================================
  TASK: Cinema Seat Booking  -  a HashMap with your own class as the key
 =======================================================================

 A cinema hall has rows A to E, with seats 1 to 6 in each row.
 You will store the bookings in a HashMap:

     key   = Seat     (a class you finish below, for example B5)
     value = String   (the name of the customer who booked it)

 STEP 1  Complete Parts A to G in main(). Each part says what to print.
 STEP 2  Run the program BEFORE writing equals() and hashCode().
         Some results will be wrong. That is expected - write down which ones.
 STEP 3  Write equals() and hashCode() in the Seat class.
 STEP 4  Run it again and compare with the EXPECTED OUTPUT below.
 STEP 5  Answer the questions at the bottom of this file.

 RULES
   - Every time you use a seat, create a NEW object: new Seat('B', 5).
     A real program works the same way: when a customer types "B5",
     the program makes a new Seat object for it.
   - Do not change printChart().

 EXPECTED OUTPUT (after STEP 3)
 The order inside { } may be different in your output. HashMap has no order.

   Bookings:           {A1=Ali, C3=Mona, A2=Sara, B5=Omar}
   Who booked C3:      Mona
   Is A2 taken:        true
   Is E6 taken:        false
   Youssef -> B5:      refused, taken by Omar
   After Ali moved:    {C3=Mona, A2=Sara, B5=Omar, D2=Ali}
   Hana -> E1:         refused, seat is broken
   Seats booked:       4

        1  2  3  4  5  6
   A    .  X  .  .  .  .
   B    .  .  .  .  X  .
   C    .  .  X  .  .  .
   D    .  X  .  .  .  .
   E    #  #  .  .  .  .
*/

class Task_CinemaBooking {

    // A seat in the cinema hall, for example B5 = row B, seat number 5.
    // static so that main() can create Seat objects directly.
    static class Seat {
        final char row;     // 'A' to 'E'
        final int number;   // 1 to 6

        Seat(char row, int number) {
            this.row = row;
            this.number = number;
        }

        @Override
        public String toString() {
            return "" + row + number;
        }

        // TODO 1: override equals()
        //   Two Seat objects are the same seat when the row AND the number are the same.
        //   The parameter must be of type Object, not Seat. Write @Override above it.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Seat)) return false;
            Seat other = (Seat) o;
            return this.row == other.row && this.number == other.number;
        }

        // TODO 2: override hashCode()
        //   Equal seats must return the same number.
        //   Use the same fields you used in equals().
        @Override
        public int hashCode() {
            return Objects.hash(row, number);
        }
    }

    public static void main(String[] args) {

        // Key = Seat, Value = name of the customer who booked it
        HashMap<Seat, String> bookings = new HashMap<>();
        HashSet<Seat> broken = new HashSet<>();

        // Part A: book these seats using put()
        //   A1 -> Ali     A2 -> Sara     B5 -> Omar     C3 -> Mona
        // Print:  Bookings:           <the map>
        bookings.put(new Seat('A', 1), "Ali");
        bookings.put(new Seat('A', 2), "Sara");
        bookings.put(new Seat('B', 5), "Omar");
        bookings.put(new Seat('C', 3), "Mona");
        System.out.println("Bookings:           " + bookings);


        // Part B: who booked seat C3?  Use get().
        // Print:  Who booked C3:      <name>
        System.out.println("Who booked C3:      " + bookings.get(new Seat('C', 3)));


        // Part C: are seats A2 and E6 taken?  Use containsKey().
        // Print:  Is A2 taken:        <true or false>
        //         Is E6 taken:        <true or false>
        System.out.println("Is A2 taken:        " + bookings.containsKey(new Seat('A', 2)));
        System.out.println("Is E6 taken:        " + bookings.containsKey(new Seat('E', 6)));


        // Part D: Youssef wants seat B5. Use putIfAbsent(), so a booked seat is not replaced.
        //   putIfAbsent() returns null if the seat was free,
        //   otherwise it returns the name that is already there.
        // Print:  Youssef -> B5:      booked
        //    or:  Youssef -> B5:      refused, taken by <name>
        String existing = bookings.putIfAbsent(new Seat('B', 5), "Youssef");
        if (existing == null) {
            System.out.println("Youssef -> B5:      booked");
        } else {
            System.out.println("Youssef -> B5:      refused, taken by " + existing);
        }


        // Part E: Ali moves from seat A1 to seat D2.  Use remove() and put().
        // Print:  After Ali moved:    <the map>
        bookings.remove(new Seat('A', 1));
        bookings.put(new Seat('D', 2), "Ali");
        System.out.println("After Ali moved:    " + bookings);


        // Part F: seats E1 and E2 are broken. Add them to the HashSet "broken".
        //   Hana wants seat E1. Book it only if it is not broken.  Use contains().
        // Print:  Hana -> E1:         booked
        //    or:  Hana -> E1:         refused, seat is broken
        broken.add(new Seat('E', 1));
        broken.add(new Seat('E', 2));
        if (broken.contains(new Seat('E', 1))) {
            System.out.println("Hana -> E1:         refused, seat is broken");
        } else {
            bookings.put(new Seat('E', 1), "Hana");
            System.out.println("Hana -> E1:         booked");
        }


        // Part G: how many seats are booked?  Use size().
        // Print:  Seats booked:       <number>
        System.out.println("Seats booked:       " + bookings.size());


        // GIVEN - prints the seating chart
        System.out.println();
        printChart(bookings, broken);
    }

    // GIVEN - do not change.  Prints the hall:  X = booked   # = broken   . = free
    static void printChart(HashMap<Seat, String> bookings, HashSet<Seat> broken) {
        System.out.println("     1  2  3  4  5  6");
        for (char row = 'A'; row <= 'E'; row++) {
            System.out.print(row + "  ");
            for (int number = 1; number <= 6; number++) {
                Seat seat = new Seat(row, number);
                if (bookings.containsKey(seat)) {
                    System.out.print("  X");
                } else if (broken.contains(seat)) {
                    System.out.print("  #");
                } else {
                    System.out.print("  .");
                }
            }
            System.out.println();
        }
    }
}

/*
 QUESTIONS - write your answers here

 Q1.Why did we have to override hashcode() and equals()?

 Q2. Delete your hashCode() but keep equals(), and run again.
     What happens? Why?

 Q3. Why are row and number final? What could go wrong if the number
     of a booked seat could be changed after it was put in the map?

 Q4. Change equals(Object o) to equals(Seat o). What happens?
     Then also remove @Override and run again. What happens now?

 BONUS. Replace the whole Seat class with this one line:
            record Seat(char row, int number) {}
        Run it. What happens, and why?

 ----------------------------------------------------------------
 ANSWERS
 ----------------------------------------------------------------

 Q1. Why did we have to override hashCode() and equals()?
 A1. HashMap uses hashCode() to choose the bucket, then equals() to find
     the matching key inside that bucket. Without overriding them, Java
     uses the default versions from Object, which compare memory addresses.
     So new Seat('B', 5) and another new Seat('B', 5) are treated as two
     different keys, even though they are the same seat. We override them
     so that two seats with the same row AND number are equal and give the
     same hash code.

 Q2. Delete hashCode() but keep equals(). What happens? Why?
 A2. The results become wrong again (get() returns null, containsKey()
     returns false, putIfAbsent() books an already taken seat, ...).
     The default hashCode() gives a different number for every object, so
     two equal seats usually go to different buckets. HashMap never even
     reaches equals(). This breaks the rule: if a.equals(b) is true, then
     a.hashCode() must equal b.hashCode().

 Q3. Why are row and number final? What could go wrong if the number of a
     booked seat could be changed after it was put in the map?
 A3. final means the values cannot change after the object is created.
     The hash code is calculated from row and number. If number changed
     after the Seat was put in the map, its hash code would change, but the
     entry would stay in the old bucket. Then get() and containsKey() would
     look in the wrong bucket and would not find it. The entry is lost
     inside the map.

 Q4. Change equals(Object o) to equals(Seat o). What happens?
     Then also remove @Override and run again. What happens now?
 A4. With @Override: compile error, because equals(Seat) does not override
     equals(Object) from the Object class.
     Without @Override: it compiles, but equals(Seat) is only an OVERLOAD,
     not an override. HashMap calls equals(Object), which is still the
     default one (compares addresses), so the results are wrong again.
     This is why @Override is useful: it shows the mistake at compile time.

 BONUS. Replace the whole Seat class with: record Seat(char row, int number) {}
 A5. It works correctly without writing equals() or hashCode(). A record
     generates equals(), hashCode() and toString() automatically from its
     fields, and its fields are final. The only difference is the output of
     toString(): it prints Seat[row=B, number=5] instead of B5.
*/
