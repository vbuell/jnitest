import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import sun.misc.Unsafe;

public class TestPin
{
        public static native Class pinStringClass();
        static
        {
                System.loadLibrary("fireapex");
        }

        public static void main(String[] args) throws Exception
        {
                Class foo = pinStringClass();
                System.out.println(getObjectAddress(foo));
                // Churn to force some GC.
                ArrayList<String> list = new ArrayList<String>();
                for (int i = 0; i < 1000000; i++)
                    list.add((String)foo.newInstance());
                System.out.println(getObjectAddress(foo));
        }

   private final static Unsafe   unsafe;
   private static final long     BITS;
   private static final long     OFF;
   private static final Object[] holder = new Object[1];

   static
   {
      try
      {
         ByteBuffer buffer = ByteBuffer.allocateDirect(1);
         Field unsafeField = buffer.getClass().getDeclaredField("unsafe");
         unsafeField.setAccessible(true);
         unsafe = (Unsafe) unsafeField.get(buffer);
         unsafeField.setAccessible(false);

         buffer.flip();
      }
      catch (Exception exc)
      {
         exc.printStackTrace();
         throw new InternalError();
      }

      BITS = unsafe.addressSize() * 8;
      OFF = unsafe.arrayBaseOffset(holder.getClass());
   }

   public static final synchronized long getObjectAddress(Object obj)
   {
      holder[0] = obj;
      if (BITS == 32)
         return unsafe.getInt(holder, OFF);
      if (BITS == 64)
         return unsafe.getLong(holder, OFF);
      throw new IllegalStateException();
   }

}
