/*
 * Sonar ESQL Plugin
 * Copyright (C) 2013-2017 Thomas Pohl and EXXETA AG
 * http://www.exxeta.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.exxeta.iss.sonar.esql.tree.impl.function;

import java.util.Collections;
import java.util.Iterator;

import com.exxeta.iss.sonar.esql.api.tree.Tree;
import com.exxeta.iss.sonar.esql.api.tree.expression.ExpressionTree;
import com.exxeta.iss.sonar.esql.api.tree.function.PassthruFunctionTree;
import com.exxeta.iss.sonar.esql.api.visitors.DoubleDispatchVisitor;
import com.exxeta.iss.sonar.esql.tree.impl.EsqlTree;
import com.exxeta.iss.sonar.esql.tree.impl.SeparatedList;
import com.exxeta.iss.sonar.esql.tree.impl.declaration.FieldReferenceTreeImpl;
import com.exxeta.iss.sonar.esql.tree.impl.declaration.ParameterListTreeImpl;
import com.exxeta.iss.sonar.esql.tree.impl.lexical.InternalSyntaxToken;
import com.google.common.base.Functions;
import com.google.common.collect.Iterators;

public class PassthruFunctionTreeImpl extends EsqlTree implements PassthruFunctionTree {

	private InternalSyntaxToken passthruKeyword;
	private InternalSyntaxToken openingParenthesis;
	private ExpressionTree expression;
	private InternalSyntaxToken toKeyword;
	private FieldReferenceTreeImpl databaseReference;
	private InternalSyntaxToken valuesKeyword;
	private ParameterListTreeImpl values;
	private SeparatedList<Tree> argumentList;
	private InternalSyntaxToken closingParenthesis;

	public PassthruFunctionTreeImpl(SeparatedList<Tree> argumentList) {
		this.argumentList = argumentList;
	}

	public PassthruFunctionTreeImpl(ExpressionTree expression, InternalSyntaxToken toKeyword,
			FieldReferenceTreeImpl databaseReference, InternalSyntaxToken valuesKeyword, ParameterListTreeImpl values) {
		super();
		this.expression = expression;
		this.toKeyword = toKeyword;
		this.databaseReference = databaseReference;
		this.valuesKeyword = valuesKeyword;
		this.values = values;
		this.argumentList = new SeparatedList<>(Collections.<Tree>emptyList(), Collections.<InternalSyntaxToken>emptyList());
	}

	public void finish(InternalSyntaxToken passthruKeyword, InternalSyntaxToken openingParenthesis,
			InternalSyntaxToken closingParenthesis) {
		this.passthruKeyword = passthruKeyword;
		this.openingParenthesis = openingParenthesis;
		this.closingParenthesis = closingParenthesis;
	}

	@Override
	public InternalSyntaxToken passthruKeyword() {
		return passthruKeyword;
	}

	@Override
	public InternalSyntaxToken openingParenthesis() {
		return openingParenthesis;
	}

	@Override
	public ExpressionTree expression() {
		return expression;
	}

	@Override
	public InternalSyntaxToken toKeyword() {
		return toKeyword;
	}

	@Override
	public FieldReferenceTreeImpl databaseReference() {
		return databaseReference;
	}

	@Override
	public InternalSyntaxToken valuesKeyword() {
		return valuesKeyword;
	}

	@Override
	public ParameterListTreeImpl values() {
		return values;
	}

	@Override
	public SeparatedList<Tree> argumentList() {
		return argumentList;
	}

	@Override
	public InternalSyntaxToken closingParenthesis() {
		return closingParenthesis;
	}

	@Override
	public void accept(DoubleDispatchVisitor visitor) {
		visitor.visitPassthruFunction(this);
	}

	@Override
	public Kind getKind() {
		return Kind.PASSTHRU_FUNCTION;
	}

	@Override
	public Iterator<Tree> childrenIterator() {
		return Iterators.concat(
				Iterators.forArray(passthruKeyword, openingParenthesis, expression, toKeyword, databaseReference,
						valuesKeyword, values),
				argumentList.elementsAndSeparators(Functions.<Tree>identity()),
				Iterators.singletonIterator(closingParenthesis));
	}

}