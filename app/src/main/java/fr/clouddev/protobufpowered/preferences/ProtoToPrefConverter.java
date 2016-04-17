package fr.clouddev.protobufpowered.preferences;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

import java.util.List;
import java.util.Map;

/**
 * Created by CopyCat on 17/04/2016.
 */
public class ProtoToPrefConverter {

    private static final String TAG = ProtoToPrefConverter.class.getSimpleName();

    public static SharedPreferences.Editor convert(Message message, SharedPreferences.Editor editor) {
        Map<Descriptors.FieldDescriptor,Object> fields = message.getAllFields();
        for (Descriptors.FieldDescriptor desc : fields.keySet()) {
            Log.i(TAG,"processing \""+desc.getName()+"\" with value \""+ fields.get(desc)+"\" ");
            switch (desc.getJavaType()) {

                case INT:
                    editor.putInt(desc.getName(),(Integer)fields.get(desc));
                    break;
                case LONG:
                    editor.putLong(desc.getName(),(Long)fields.get(desc));
                    break;
                case FLOAT:
                    break;
                case DOUBLE:
                    break;
                case BOOLEAN:
                    editor.putBoolean(desc.getName(),(Boolean)fields.get(desc));
                    break;
                case STRING:
                    editor.putString(desc.getName(),(String)fields.get(desc));
                    break;
                case BYTE_STRING:
                    break;
                case ENUM:
                    break;
                case MESSAGE:
                    break;
            }

        }
        return editor;
    }

    public static Message.Builder convertFromPrefs(Message.Builder builder, SharedPreferences prefs) {
        List<Descriptors.FieldDescriptor> fields = builder.getDescriptorForType().getFields();
        for (Descriptors.FieldDescriptor desc : fields) {
            Log.i(TAG,"processing \""+desc.getName()+"\"");
            switch (desc.getJavaType()) {

                case INT:
                    builder.setField(desc,prefs.getInt(desc.getName(),0));
                    break;
                case LONG:
                    builder.setField(desc,prefs.getLong(desc.getName(),0L));
                    break;
                case FLOAT:
                    break;
                case DOUBLE:
                    break;
                case BOOLEAN:
                    builder.setField(desc,prefs.getBoolean(desc.getName(),false));
                    break;
                case STRING:
                    builder.setField(desc,prefs.getString(desc.getName(),""));
                    break;
                case BYTE_STRING:
                    break;
                case ENUM:
                    break;
                case MESSAGE:
                    break;
            }

        }
        return builder;
    }
}
