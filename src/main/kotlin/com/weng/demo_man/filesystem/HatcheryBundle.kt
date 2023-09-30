package com.weng.demo_man.filesystem

import com.intellij.AbstractBundle
import org.jetbrains.annotations.PropertyKey

object HatcheryBundle : AbstractBundle(BUNDLE) {
  fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any) = getMessage(key, *params)
}