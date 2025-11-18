package me.supcheg.advancedgui.code.processor;

import com.palantir.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

import static me.supcheg.advancedgui.code.processor.TypeNames.rename;

class NamesResolver {

    Names namesForObject(TypeMirror objectType) {
        return namesForObject(TypeName.get(objectType));
    }

    Names namesForObject(TypeName objectTypename) {
        return new Names(
                objectTypename,
                rename(objectTypename, name -> name + "Impl"),
                rename(objectTypename, name -> name + "Builder"),
                rename(objectTypename, name -> name + "BuilderImpl")
        );
    }
}
