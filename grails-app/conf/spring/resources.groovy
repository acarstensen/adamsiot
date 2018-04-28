import com.adamsiot.SystemPasswordEncoderListener
// Place your Spring DSL code here
beans = {
    systemPasswordEncoderListener(SystemPasswordEncoderListener, ref('hibernateDatastore'))
}
