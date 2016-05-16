#include <jni.h>

JNIEXPORT jclass JNICALL Java_TestPin_pinStringClass(JNIEnv * env, jclass clazz)
 {
     static jclass stringClass = NULL;

     if (stringClass == NULL) {
         jclass localRefCls =
             (*env)->FindClass(env, "java/lang/String");
         if (localRefCls == NULL) {
             return NULL; /* exception thrown */
         }
         /* Create a global reference */
         stringClass = (*env)->NewGlobalRef(env, localRefCls);

         /* The local reference is no longer useful */
         (*env)->DeleteLocalRef(env, localRefCls);

         /* Is the global reference created successfully? */
         if (stringClass == NULL) {
             return NULL; /* out of memory exception thrown */
         }
     }
}
