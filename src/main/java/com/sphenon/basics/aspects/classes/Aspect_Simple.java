package com.sphenon.basics.aspects.classes;

/****************************************************************************
  Copyright 2001-2024 Sphenon GmbH

  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations
  under the License.
*****************************************************************************/

import com.sphenon.basics.context.*;
import com.sphenon.basics.context.classes.*;
import com.sphenon.basics.message.*;
import com.sphenon.basics.notification.*;
import com.sphenon.basics.configuration.*;
import com.sphenon.basics.customary.*;

import com.sphenon.basics.aspects.*;

public class Aspect_Simple implements Aspect {
    static protected Configuration config;
    static { config = Configuration.create(RootContext.getInitialisationContext(), "com.sphenon.basics.aspects.Aspect"); };

    static protected java.util.Hashtable aspects;
    protected String name;
    protected String[] names;

    public String getName(CallContext context) {
        return this.name;
    }

    public String[] getNames(CallContext context) {
        return this.names;
    }

    protected Aspect_Simple (CallContext call_context, String name) {
        this.name = name;
        this.names = (this.name == null || this.name.length() == 0 ? new String[0] : this.name.split("\\|"));
    }

    static public Aspect_Simple create(CallContext context, String name) {
        if (aspects == null) {
            aspects = new java.util.Hashtable();
        } else {
            Object o = aspects.get(name);
            if (o != null) {
                return (Aspect_Simple) o;
            }
        }
        Aspect_Simple as = new Aspect_Simple(context, name);
        aspects.put(name, as);
        return as;
    }

    public String getProperty(CallContext context, String property_name, String default_value) {
        String property_value;
        for (String aspect_name : this.names) {
            property_value = config.get(context, "PROPERTY.ASPECT." + aspect_name + "." + property_name, (String) null);
            if (property_value != null) {
                return property_value;
            }
        }
        return config.get(context, "PROPERTY.DEFAULT." + property_name, default_value);
    }
}
