// This is a generated file. Not intended for manual editing.
package com.weng.demo_man.pkt.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;

import static com.intellij.lang.parser.GeneratedParserUtilBase.*;

import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;
import com.weng.demo_man.pkt.psi.ROSPktTypes;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class ROSPktParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return rosPktFile(b, l + 1);
  }

  /* ********************************************************** */
  // type_ LBRACKET NUMBER? RBRACKET
  public static boolean array_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_type")) return false;
    if (!nextTokenIs(b, "<array type>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.TYPE, "<array type>");
    r = type_(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.LBRACKET);
    r = r && array_type_2(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.RBRACKET);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NUMBER?
  private static boolean array_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "array_type_2")) return false;
    GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.NUMBER);
    return true;
  }

  /* ********************************************************** */
  // LINE_COMMENT
  public static boolean comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, ROSPktTypes.LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.LINE_COMMENT);
    exit_section_(b, m, ROSPktTypes.COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // NEG_OPERATOR? NUMBER | STRING
  public static boolean const_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_$")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.CONST, "<const $>");
    r = const_0(b, l + 1);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.STRING);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NEG_OPERATOR? NUMBER
  private static boolean const_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = const_0_0(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.NUMBER);
    exit_section_(b, m, null, r);
    return r;
  }

  // NEG_OPERATOR?
  private static boolean const_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_0_0")) return false;
    GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.NEG_OPERATOR);
    return true;
  }

  /* ********************************************************** */
  // type_valid_ label CONST_ASSIGNER const
  public static boolean const_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_field")) return false;
    if (!nextTokenIs(b, "<const field>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.FIELD, "<const field>");
    r = type_valid_(b, l + 1);
    r = r && label(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.CONST_ASSIGNER);
    r = r && const_$(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type_any_ label (CONST_ASSIGNER const | CONST_ASSIGNER | const)
  public static boolean const_field_frag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_field_frag")) return false;
    if (!nextTokenIs(b, "<const field frag>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.FIELD_FRAG, "<const field frag>");
    r = type_any_(b, l + 1);
    r = r && label(b, l + 1);
    r = r && const_field_frag_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // CONST_ASSIGNER const | CONST_ASSIGNER | const
  private static boolean const_field_frag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_field_frag_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = const_field_frag_2_0(b, l + 1);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.CONST_ASSIGNER);
    if (!r) r = const_$(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // CONST_ASSIGNER const
  private static boolean const_field_frag_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "const_field_frag_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.CONST_ASSIGNER);
    r = r && const_$(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // const_field|const_field_frag|short_field|short_field_frag
  static boolean field_component_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_component_")) return false;
    if (!nextTokenIs(b, "", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    r = const_field(b, l + 1);
    if (!r) r = const_field_frag(b, l + 1);
    if (!r) r = short_field(b, l + 1);
    if (!r) r = short_field_frag(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // NAME
  public static boolean label(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "label")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, ROSPktTypes.NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.NAME);
    exit_section_(b, m, ROSPktTypes.LABEL, r);
    return r;
  }

  /* ********************************************************** */
  // sectioned_item_* section?
  static boolean rosPktFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rosPktFile")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = rosPktFile_0(b, l + 1);
    r = r && rosPktFile_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // sectioned_item_*
  private static boolean rosPktFile_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rosPktFile_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!sectioned_item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "rosPktFile_0", c)) break;
    }
    return true;
  }

  // section?
  private static boolean rosPktFile_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rosPktFile_1")) return false;
    section(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // (field_component_|comment|CRLF)*
  public static boolean section(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section")) return false;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.SECTION, "<section>");
    while (true) {
      int c = current_position_(b);
      if (!section_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "section", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  // field_component_|comment|CRLF
  private static boolean section_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "section_0")) return false;
    boolean r;
    r = field_component_(b, l + 1);
    if (!r) r = comment(b, l + 1);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.CRLF);
    return r;
  }

  /* ********************************************************** */
  // section? separator
  static boolean sectioned_item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sectioned_item_")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = sectioned_item__0(b, l + 1);
    r = r && separator(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // section?
  private static boolean sectioned_item__0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "sectioned_item__0")) return false;
    section(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // SERVICE_SEPARATOR
  public static boolean separator(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "separator")) return false;
    if (!GeneratedParserUtilBase.nextTokenIs(b, ROSPktTypes.SERVICE_SEPARATOR)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.SERVICE_SEPARATOR);
    exit_section_(b, m, ROSPktTypes.SEPARATOR, r);
    return r;
  }

  /* ********************************************************** */
  // type_valid_ label
  public static boolean short_field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "short_field")) return false;
    if (!nextTokenIs(b, "<short field>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.FIELD, "<short field>");
    r = type_valid_(b, l + 1);
    r = r && label(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // type_any_ label?
  public static boolean short_field_frag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "short_field_frag")) return false;
    if (!nextTokenIs(b, "<short field frag>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.FIELD_FRAG, "<short field frag>");
    r = type_any_(b, l + 1);
    r = r && short_field_frag_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // label?
  private static boolean short_field_frag_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "short_field_frag_1")) return false;
    label(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // type_
  public static boolean short_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "short_type")) return false;
    if (!nextTokenIs(b, "<short type>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.TYPE, "<short type>");
    r = type_(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // CUSTOM_TYPE|KEYTYPE
  static boolean type_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_")) return false;
    if (!nextTokenIs(b, "", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.CUSTOM_TYPE);
    if (!r) r = GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.KEYTYPE);
    return r;
  }

  /* ********************************************************** */
  // array_type|type_frag|short_type
  static boolean type_any_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_any_")) return false;
    if (!nextTokenIs(b, "", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    r = array_type(b, l + 1);
    if (!r) r = type_frag(b, l + 1);
    if (!r) r = short_type(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // type_ LBRACKET NUMBER?
  public static boolean type_frag(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_frag")) return false;
    if (!nextTokenIs(b, "<type frag>", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ROSPktTypes.TYPE_FRAG, "<type frag>");
    r = type_(b, l + 1);
    r = r && GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.LBRACKET);
    r = r && type_frag_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // NUMBER?
  private static boolean type_frag_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_frag_2")) return false;
    GeneratedParserUtilBase.consumeToken(b, ROSPktTypes.NUMBER);
    return true;
  }

  /* ********************************************************** */
  // array_type|short_type
  static boolean type_valid_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_valid_")) return false;
    if (!nextTokenIs(b, "", ROSPktTypes.CUSTOM_TYPE, ROSPktTypes.KEYTYPE)) return false;
    boolean r;
    r = array_type(b, l + 1);
    if (!r) r = short_type(b, l + 1);
    return r;
  }

}
