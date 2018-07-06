package com.sphenon.basics.aspects;

/****************************************************************************
  Copyright 2001-2018 Sphenon GmbH

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
import com.sphenon.basics.message.*;
import com.sphenon.basics.aspects.classes.*;

public class AspectsContext extends SpecificContext {

    static public AspectsContext getOrCreate(Context context) {
        AspectsContext aspects_context = (AspectsContext) context.getSpecificContext(AspectsContext.class);
        if (aspects_context == null) {
            aspects_context = new AspectsContext(context);
            context.setSpecificContext(AspectsContext.class, aspects_context);
        }
        return aspects_context;
    }

    static public AspectsContext get(Context context) {
        return (AspectsContext) context.getSpecificContext(AspectsContext.class);
    }

    static public AspectsContext create(Context context) {
        AspectsContext ac = new AspectsContext(context);
        context.setSpecificContext(AspectsContext.class, ac);
        return ac;
    }

    protected AspectsContext (Context context) {
        super(context);
    }

    protected Aspect aspect;
    static protected Aspect default_aspect;

    static protected Aspect getDefaultAspect(CallContext cc) {
        return (default_aspect != null ? default_aspect
                                         : (default_aspect = Aspect_Simple.create(cc, "Default"))
               );
    }

    public void setAspect(CallContext cc, Aspect aspect) {
        this.aspect = aspect;
    }

    public Aspect getAspect(CallContext cc) {
        AspectsContext ac;
        return (this.aspect != null ?
                  this.aspect
                : (ac = (AspectsContext) this.getCallContext(AspectsContext.class)) != null ?
                   ac.getAspect(cc)
                : this.getDefaultAspect(cc)
               );
    }
}
