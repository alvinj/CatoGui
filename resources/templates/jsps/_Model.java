package **PACKAGE**;

import org.apache.struts.action.*;

public class **CLASS_NAME**Form extends ActionForm
{
  **FOREACH_FIELD**
  private **FIELD_DATA_TYPE** **FIELD_NAME**;
  **END_FOREACH_FIELD**

  **FOREACH_FIELD**
  public **FIELD_DATA_TYPE** get**FIELD_LABEL**()
  {
    return this.**FIELD_NAME**;
  }

  **END_FOREACH_FIELD**
  **FOREACH_FIELD**
  public void set**FIELD_LABEL**(**FIELD_DATA_TYPE** **FIELD_NAME**)
  {
    this.**FIELD_NAME** = **FIELD_NAME**;
  }

  **END_FOREACH_FIELD**
}
