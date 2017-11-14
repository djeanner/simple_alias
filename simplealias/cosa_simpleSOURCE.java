

//char in_ip[5000];
//char buff3[5000];
//char in_name[1000];
//char *pointbuff;
//double *protdim

////  CCCOORRREECCCTT INPUTTTTT ////
//public int dump2(double sw,int td,int sii,double *ol,double *addelta,double *ppm,double o2,char *in_name,char *bufh,char *bufg,char *bufftma, double in_o2,char *in_refd1,char *in_refd2,char *in_refd3,double in_freq,double in_sr,int si2,double refmaxt1 ){
public int dump2(double sw,int td,int sii,double refmaxt1 ){

double tmpd3;
double tmpd4;
double	si;
int i,j,k;
double tmpp1,tmpp2,tmpp3,nsw;
double max2,min2;
double last,first;
/*********************************/
//char bufg2[20000];
//char bufftma2[20000];
//char bbuu[20000];
//int intdiv[50];
//int nbfr,frfr[5000];
//double frin[5000],frout[5000];
//char bufftmi[20000];
int l;
int n,m,mm,nn,o;
int maddx=3,addx,am;
int dei=100,dem=1500,deo=5000,des=1000,flag;
si=(double)sii;
flag=0;
/*********************************/
if(!www) fprintf(stdout,"SIMPLE%lf %d %d %lf %s\nSIMPLE%s %s %s none.none 777555 damien.jeannerat@chiorg.unige.ch\nSIMPLE%lf %lf %d\n",sw,td,sii,in_o2,in_name,in_refd1,in_refd2,in_refd3,in_freq,in_sr,nbc);
/*********************************/
	strcpy(bufg2,bufg);
	strcat(bufg2,in_name);
	strcat(bufg2,".xwp");
	strcpy(bufftma2,bufftma);
	strcat(bufftma2,in_name);
	strcat(bufftma2,".xwp");
fprintf(f_ou2 ,"\nA spectrum with the following acquisition parameters resolves all regions in the carbon dimension.\n");
fprintf(f_ou2 ,"=====> Number of time increments : 1 TD = %d points\n=====> Spectral width               SWh = %lf Hz (SW=%lf ppm) in the carbon dimension\n       (Do not truncate the value of the spectral widht!)\nThe digital resolution is dr = %lf Hz/point.\nThe carrier frequency set to o2p = %.2lf ppm.\n",td,sw,sw/in_freq,sw/(double)td,in_o2);
fprintf(f_ou2 ,"The longest t1 time evolution is t1,max = %lf\nNote that it is less then 1.1*t1,max of the full spectrum (1.1*%lf)\n",(double)td/sw/2.0,refmaxt1);
if((double)td/sw/2.0>0.1)
fprintf(f_ou2 ,"Warning: t1,max is more than 100 ms. When using experiments with gradient coding during t1 (like the standard HMBC exp), diffusion may decrease SNR. See Helvetica Chemica Acta Vol 87 (2004) pp 2190-2207 for discussion and a solution.\n");
if((double)td/sw/2.0>0.5)
fprintf(f_ou2 ,"Warning: t1,max is more than 500 ms. This time evolution may be too long if your molecules relaxes quickly. If this is the case, use a smaller value of TD.\n");
fprintf(f_ou2 ,"Use States, States/TPPI or Echo/Anti-Echo as quadrature in F1\n");
nbfr=0;
min2=0;
if(www)fprintf(f_ou2,"\nPosition of signals in the carbon dimension of the aliased spectrum \n\nRegion                    Center   Nat.LW  Proc.LW      side-to-side       SI    delta \n"); 
for(j=0;j<nbc;j++){
	max2=1e8;
	for(k=0;k<nbc;k++){
		nsw=(*(ol+k)-o2+sw/2);nsw=fmod(nsw,sw);if(nsw<0) nsw=(sw+nsw);
		if(nsw<max2 && nsw>min2) {max2=nsw;i=k;} /* starts from top */
	}
	nsw=(*(ol+i)-o2+sw/2);nsw=fmod(nsw,sw);if(nsw<0) nsw=sw+nsw;
	fprintf(f_ou2 ,"# %3d: %10.5lf ppm, ",i+1,*(ppm+i));
	tmpp1=*(addelta+i)*falv;
	tmpp2=2.0*sw/td*fadr;
	tmpp3=tmpp1+tmpp2;
if(!www)fprintf(f_ou2,"(%7.2lf %7.2lf %7.2lf %7.2lf-%7.2lf/%7.2lf)",nsw,2.0*tmpp1,2.0*tmpp2,nsw-tmpp3,nsw+tmpp3,sw);
if(!www)fprintf(f_ou2,"(%7.2lf %7.2lf %7.2lf %7.2lf-%7.2lf/%4.0lf)",si-(nsw/sw*si),2.0*tmpp1/sw*si,2.0*tmpp2/sw*si,si-(nsw/sw*si-tmpp3/sw*si),si-(nsw/sw*si+tmpp3/sw*si),si);
if(www)fprintf(f_ou2," %8.2lf %8.2lf %8.2lf %8.2lf-%8.2lf / %6.0lf",si-(nsw/sw*si),2.0*tmpp1/sw*si,2.0*tmpp2/sw*si,si-(nsw/sw*si-tmpp3/sw*si),si-(nsw/sw*si+tmpp3/sw*si),si);
	frin[nbfr] =si-(nsw/sw*si-tmpp3/sw*si);
	frout[nbfr]=si-(nsw/sw*si+tmpp3/sw*si);
	frfr[nbfr]=i+1;nbfr++;
	if(j>0) fprintf(f_ou2 ,"%9.4lf Hz",(last- (si-(nsw/sw*si-tmpp3/sw*si)))/si*sw);/* diff with signal above -control really reparated */
	if(j>0) if(si-(nsw/sw*si-tmpp3/sw*si) > last) fprintf(f_ou2 ,"^Ov");
	last=		si-(nsw/sw*si+tmpp3/sw*si);
	if(j==nbc-1) {
		tmpp1=si-(nsw/sw*si+tmpp3/sw*si);if(tmpp1<0) tmpp1=(double)si-tmpp1; else tmpp1=si+tmpp1;
		if(tmpp1<first) fprintf(f_ou2 ,"_Ov");
	}
	fprintf(f_ou2 ,"\n");
	if(j==0) first= si-(nsw/sw*si-tmpp3/sw*si);
	min2=nsw;
}
if(www)fprintf(f_ou2,"\n"); 
if(www)fprintf(f_ou2,"Center       : Position of the center of signals (in pt)\n"); 
if(www)fprintf(f_ou2,"Nat.LW       : Contribution due to the natural line width (in pt) \n");
if(www)fprintf(f_ou2,"               By default the it is 1.2 Hz width at half-height (contact the author for using other values)\n");
if(www)fprintf(f_ou2,"Proc.LW      : Contribution to the signal width due to the processing (in pt)\n");
if(www)fprintf(f_ou2,"               2.0*SWa/TDa where 2.0 is empirical \n");
if(www)fprintf(f_ou2,"side-to-side : Spectral region expected to be covered by the signal (in pt)\n");
if(www)fprintf(f_ou2,"SI           : Number of points to use for the processing in the carbon dimension (next power of 2 with respect to TDa)\n");
if(www)fprintf(f_ou2,"delta        : Distance between top of this signal and botom of the signal above\n");
if(www)fprintf(f_ou2,"               Negative distances (highlighted with '^Ov' or '_Ov') indicate overlap (\n\n");

/* portfolio part */
am=10000000;

if((filein=fopen(bufh,"r"))!=NULL){
fprintf(f_ou2 ,"xwinnmr file: %s\n",bufftma2); 
	if((f_ou3=fopen(bufg2,"w"))!=NULL){
		for(i=0;i>-1;i++){
			l=fgets(bbuu,10000,filein)-bbuu;
			pointbuff=bbuu;
			if(l!=0) break;
			j=strstr(bbuu,"Spec2DProjGraph")-bbuu;
			if(j>=0) {
sscanf(bbuu,"%s %d %d %d %d (%d|%d) (%d|%d)",bufftmi,&intdiv[0],&intdiv[1],&intdiv[2],&intdiv[3],&intdiv[4],&intdiv[5],&intdiv[6],&intdiv[7]); 
			}
			j=strstr(bbuu,"ArrowPolyLine")-bbuu;
			if(j>=0) {
sscanf(bbuu,"%s %d %d ",bufftmi,&intdiv[8],&intdiv[9]); 
				for(j=0;j<nbfr;j++){
					m=((intdiv[5]-intdiv[7])*( frin[j]+0)/si)+intdiv[7];
					n=((intdiv[5]-intdiv[7])*(frout[j]+0)/si)+intdiv[7];/* was +1 instead of +0 before*/
					o=(intdiv[5]-intdiv[7])*(ppmm[frfr[j]-1]-min)/(max-min)+intdiv[7];
					mm=intdiv[4];
					nn=intdiv[6];
					/* for box OK otherwise make average*/
					m=(m+n)/2;n=m;
					/* for additional displacement in x*/
					if((am-m)<200) addx++; else addx=0;if(addx>=maddx) addx=0;
					am=m;
					/* end additional displacement in x*/
/* for each region **********************************/
for(k=0;k<(int)*(protdim+5000*(frfr[j]-1));k++){
	m=((intdiv[5]-intdiv[7])*( frin[j]+0)/si)+intdiv[7];
	n=((intdiv[5]-intdiv[7])*(frout[j]+0)/si)+intdiv[7];/* was +1 instead of +0 before*/
	tmpd3=*(protdim+5000*(frfr[j]-1)+1+k*2);
	tmpd4=*(protdim+5000*(frfr[j]-1)+1+k*2+1);	
	mm=(intdiv[6]-intdiv[4])*(tmpd3/(double)si2)+intdiv[4];
	nn=(intdiv[6]-intdiv[4])*(tmpd4/(double)si2)+intdiv[4];
/*fprintf(stdout,"SIMPLEbummm %d  %d  %d  \n",intdiv[6],intdiv[4],mm);*/
if(flag)fprintf(f_ou3,"ArrowPolyLine %d 5 (%d|%d)  (%d|%d) (%d|%d) (%d|%d)  (%d|%d) 500 0\n",intdiv[8],mm,m,mm,n,nn,n,nn,m,mm,m);
if(flag)fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"C%2d\" 190.00 t\n",intdiv[8],nn+200,m-0*100,nn+dei+dem,n-0*100,frfr[j]);
	}
m=(m+n)/2;n=m;
mm=intdiv[4];
nn=intdiv[6];
/* end for each region **********************************/


if(!flag)				fprintf(f_ou3,"ArrowPolyLine %d 2 (%d|%d)  (%d|%d) 500 0\n",intdiv[8],mm,n,nn+ dem*addx,m);
if(!flag)				fprintf(f_ou3,"ArrowPolyLine %d 4 (%d|%d)  (%d|%d) ",intdiv[8],nn+ dem*addx+dem,n,nn+ dem*maddx,m);
if(!flag)				fprintf(f_ou3,"(%d|%d) (%d|%d) 500 0\n",nn+dem*maddx+deo,o,nn+maddx*dem+deo+des,o);
if(!flag)				fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"C%2d-%7.3f\" 190.00 t\n",intdiv[8],nn+ dem*addx+200,m-100,nn+dem+dem*addx+dei,n-100,frfr[j],ppmm[frfr[j]-1]);
				}
if(!flag)	fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"Simple alias SW_h=%lf TD=%d SI=%d max t1=%.4lf\" 200.00 t\n",intdiv[8],intdiv[4],intdiv[5]+4400,intdiv[4]+20000,intdiv[5]+4200,sw  ,td    ,sii,(double)td    /(2.0*sw  ));
/*if(!flag)	fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"The same resolution would require TD=%.0lf (for SW_h=%.3lf Hz or SW=%.1lf ppm). Save a factor %.1lf in acquisition time when not limitted by concentration.\" 200.00 t\n",intdiv[8],intdiv[4],intdiv[5]+4200,intdiv[4]+20000,intdiv[5]+4000,(((double)td    )*sw1h_2dr/sw  ),sw1h_2dr,in_sw,(sw1h_2dr/sw  )); NOT TRUE OR ONLY WHEN SAME MAX t1*/
if(!flag)	fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"Ref. Jeannerat, (in preparation) and Jeannerat, Magn. Reson. in Chem. Vol.41 p3-17 (2002).\" 200.00 t\n",intdiv[8],intdiv[4],intdiv[5]+4000,intdiv[4]+20000,intdiv[5]+3800);

if(!flag)				fprintf(f_ou3,"ArrowPolyLine %d 2 (%d|%d)  (%d|%d) 500 0\n",intdiv[8],	nn+maddx*dem+deo,intdiv[5],\
													nn+maddx*dem+deo,intdiv[7]);
if(!flag)	fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"C%2d-%7.3f ppm\" 190.00 t\n",intdiv[8],\
				nn+maddx*dem+deo    ,intdiv[5],\
				nn+maddx*dem+deo+des,intdiv[5], 0+1,max);
if(!flag)	fprintf(f_ou3,"ResizeableText %d (%d|%d)  (%d|%d) 2 0 0 \"C%2d-%7.3f ppm\" 190.00 t\n",intdiv[8],\
				nn+maddx*dem+deo    ,intdiv[7]-200,\
				nn+maddx*dem+deo+des,intdiv[7]-200, nbc,min);
	flag=1;
			}else{
				fprintf(f_ou3,"%s",bbuu);
			}
		}
	fclose(f_ou3);
	}
	fclose(filein);
}else{
fprintf(f_ou2,"No Bruker xwinplot template was found (contact damien.jeannerat@chiorg.unige.ch to submit one)\n");
	}
/* end portfolio part */
if(!www){
for(i=0;i<nbc;i++) {
		for(j=0;j<nbfr;j++) if((frfr[j]-1)==i) break;if(j>=nbc) {/* problem */ fprintf(stdout,"SIMPLE PROBLEM i=%d j=%d\n",i,j);j=0;
for(j=0;j<nbfr;j++)  fprintf(stdout,"SIMPLE PROBLEM frfr[%d=%d]\n",j,frfr[j]);
}


	fprintf(stdout,"SIMPLE%lf %lf %d %lf %lf ",ppmm[i],addelta[i],(int)((frin[j]+frout[j])/2+1),frin[j],frout[j]);
	for(j=0;j<(int)*(protdim+5000*i);j++){
		fprintf(stdout,"%lf-%lf ",*(protdim+5000*i+1+j*2),*(protdim+5000*i+1+j*2+1));
                }
                fprintf(stdout,"end\n");
        }
}
/*if(!www){
for(i=0;i<nbfr;i++) {
	fprintf(stdout,"SIMPLE%lf %lf %d %lf %lf ",ppmm[frfr[i]-1],addelta[frfr[i]-1],(int)((frin[i]+frout[i])/2+1),frin[i],frout[i]);
	for(j=0;j<(int)*(protdim+5000*(frfr[i]-1));j++){
		fprintf(stdout,"%lf-%lf ",*(protdim+5000*(frfr[i]-1)+1+j*2),*(protdim+5000*(frfr[i]-1)+1+j*2+1));
                }
                fprintf(stdout,"end\n");
        }
}*/
return(0);
}

//////////int main(arg,argv)
//////////int arg;
//////////char *argv[];
//////////{

char opt[100];
char bufh[20000];
char bufg[20000];
char bufftma[20000];
char bufftmi[20000];

time_t	tt1;
char in_email[5000];
char in_refd1[5000];
char in_refd2[5000];
char in_refd3[5000];
char bufftmp[5000];
char buff[50000];
char buf2[50000];
double *ppm,in_o2,in_freq,in_sr;
double addelta[1000];
double ol[1000];
double sw,nsw;
double o2,delta;
int *is;
////double swsw=0,swswt1,swswop;
/******** end form preal ***************/
/*anal  */
/*end anal  */

fscanf(stdin,"%lf %d %d %lf %s %s %s %s %s %d %s %lf %lf %d",&singp,&singptd,&in_si,&in_o2,opt,in_refd1,in_refd2,in_refd3,in_ip,&in_id,in_email,&in_freq,&in_sr,&nbc);
if(in_si<0) www=1;else www=0;

firstfound=0;
/*if(www) fprintf(stdout,"Content-type: text/html\n\n");
//if(www) fprintf(stdout,"<HTML>\n");
//if(www) fprintf(stdout,"<HEAD><TITLE>\n");
//	fprintf(stdout,"Results of Simple Alias (version 2.1)\n");
//if(www) fprintf(stdout,"</TITLE></HEAD>\n");*/
if(www) fprintf(stdout,"<BODY>\n");
if(www) fprintf(stdout,"<PRE>\n");
if(debug) fprintf(stdout,"<H4>line %s</H4>\n",buff);

/*********************************************************************************************************/

ppm=malloc((sizeof(double)*1000));
protdim=malloc((sizeof(double)*nbc*5000));
for(i=0;i<nbc;i++){
	fscanf(stdin,"%lf %lf %d %lf %lf %s",(ppm+i),&addelta[i],&j,&tmpd,&tmpd,buf2);
	k=1;
	while(strstr(buf2,"end")==0){
	fprintf(stdout,"%d up ",i); /*DEB*/
		sscanf(buf2,"%lf-%lf",protdim+5000*i+k,protdim+5000*i+k+1);k+=2;if(k>=5000) k-=2;
		fscanf(stdin,"%s",buf2);
	}
	*(protdim+5000*i)=(double)(k-1)/2;
}

/*********************************************************************************************************/

if(www){
if(!scsg28) strcpy(bufftmp,"/usr/people/guest/pref_");
else strcpy(bufftmp,"/usr/people/www/htdocs/simplealias_data/pref_");
strcat(bufftmp,in_ip);
if((f_out=fopen(bufftmp,"w"))!=NULL){
	fprintf(f_out,"%lf 0.0 %lf %d %s\n",in_o2,in_freq,in_nbcol,in_email);
	fclose(f_out);
}
}

//ppmm[] chemical shifts in ppm
//ol[] chemical shifts in Hz
//nbc number of signals

/* create Hz and ppmm table and signal width */

fprintf(stdout,"Input list : \n",i+1,ppm[i],ol[i],2*addelta[i]);

fprintf(stdout,"\n");
..........

if(pr) fprintf(stdout,"Note that the list below has been shortened and number changed !\nSome regions contain more than one signal.\n");
if(pr) for(i=0;i<nbc;i++) {
	fprintf(stdout,"Region #%3d :  %10.4lf ppm (%12.2lf Hz) width: %4.1lf Hz\n",i+1,ppm[i],ol[i],2*addelta[i]);
}

fflush(stdout);
fprintf(stdout,"\nCalculations are running... \nThe results will be mailed to you in a while...\n");
if(www)fprintf(stdout,"</PRE>\n");
if(www)fprintf(stdout,"</BODY>\n");
if(www)fprintf(stdout,"</HTML>\n");
fflush(stdout);

/************* si in proton dimension ********/
si2=1*1024;
/*********************************************/

o2=in_o2*in_freq; /* other way to calculate */


/*storenbb=((int)(nbc/16))+1;*/
/*storesw =  malloc((unsigned)( 50000 * sizeof(double)));
storeov =  malloc((unsigned)( 50000 * sizeof(int)));
storecov=malloc((unsigned)( 50000*storenbb * sizeof(unsigned int)));*/

is =  malloc((unsigned)(  nbc  *si2 * sizeof(int)));
for(i=0;i<nbc;i++) *(is+i)=1;

/* check that calculation below is correct ! */
in_sw=max-min+0.00001;
sw1h_2dr=in_sw*in_freq;


if(!scsg28) strcpy(bufftmp,"/usr/people/guest/results_");
else strcpy(bufftmp,"/usr/people/www/htdocs/simplealias_data/results_");
strcat(bufftmp,in_ip);
strcat(bufftmp,"_");
sprintf(buff,"%d",getpid());
strcat(bufftmp,buff);
f_out=fopen(bufftmp,"w");
for(i=0;i<nbc;i++) {
	fprintf(f_out,"Region #%3d :  %9.3lf ppm (%12.2lf Hz) width: %4.1lf Hz\n",i+1,ppm[i],ol[i],2*addelta[i]);
}
fprintf(f_out,"DATA in_sr=%lf, in_o2=%lf, in_sw=%lf, in_freq=%lf, in_email=%s, in_ip=%s)\n",in_sr,in_o2,in_sw,in_freq,in_email,in_ip);
if(!scsg28)strcpy(bufftmp,"/usr/people/guest/mailed_");
else strcpy(bufftmp,"/usr/people/www/htdocs/simplealias_data/mailed_");
strcat(bufftmp,in_ip);
strcat(bufftmp,"_");
sprintf(buff,"%d",getpid());
strcat(bufftmp,buff);
f_ou2=fopen(bufftmp,"w");
fprintf(f_out,"Results of calculation for %s acqu#:%s proc#:%s\n",in_refd1,in_refd2,in_refd3);
fprintf(f_ou2,"Results of calculation for %s acqu#:%s proc#:%s\n",in_refd1,in_refd2,in_refd3);
			
/* init filename for portfolio files */
if(www){
if(!scsg28)strcpy(bufh,"/usr/people/guest/port_");
else strcpy(bufh,"/usr/people/www/htdocs/simplealias_data/port_");
	strcat(bufh,in_ip);
	sprintf(bufftmi,"%d",getpid());
if(!scsg28)strcpy(bufg,"/usr/people/guest/");
else strcpy(bufg,"/usr/people/www/htdocs/simplealias_data/");
	strcat(	bufg,bufftmi);
	sprintf(bufftmi,"%d",getpid());
	strcpy(bufftma,"\nhttp://rmn.unige.ch/simplealias_data/");
	strcat(bufftma,bufftmi);
}else{
if(!scsg28)strcpy(bufh,"/Bruker/31/plot/layouts/simplealias_template.xwp");
else strcpy(bufh,"/u/plot/layouts/simplealias_template.xwp");
if(!scsg28)strcpy(bufg,"/Bruker/31/plot/layouts/simplealias"); 
else strcpy(bufg,"/u/plot/layouts/simplealias"); 
	strcpy(bufftma,"");
	
}
/* move from dump2*/

/*START addition analyitcal solution */
sasdi=  malloc((unsigned) nbc*nbc*sizeof(double));
sasdo=  malloc((unsigned) nbc*nbc*sizeof(double));
sasi=  malloc((unsigned) nbc*nbc*sizeof(int   ));
/*                                                       */
/*           ____________________                        */
/*          |                    |                       */
/*          |                    |                       */
/*          |                    |                       */
/* _________|                    |___________            */
/*                    ^                                  */
/*                                                       */

/*si&factor not used instead td*/
tmpd=sw1h_2dr;
for(i=0;i<nbc;i++) {
	if(debug2) fprintf(stdout,"i=%d :",i);/* ddump */
	for(j=i+1;j<nbc;j++) {
		if(debug2) fprintf(stdout,"%.3lf ",fabs(ol[i]-ol[j]));
		if(ol[i]>ol[j]) if(tmpd>((ol[i]-addelta[i])-(ol[j]+addelta[j]))) {
			tmpd=           ((ol[i]-addelta[i])-(ol[j]+addelta[j])); ;tmpi=i;tmpa=j; }
		if(ol[i]<ol[j]) if(tmpd>((ol[j]-addelta[j])-(ol[i]+addelta[i]))) {
			tmpd=           ((ol[j]-addelta[j])-(ol[i]+addelta[i])); ;tmpi=j;tmpa=i; }
	}
	if(debug2) fprintf(stdout,"\n");
}

/*neglects line width and width due to proc*/

//fprintf(stdout,"The distance between the closest pair of regions to separate is %10.4lf Hz (between R%d:%lf and R%d:%lf ppm)\n",tmpd,tmpi+1,ppm[tmpi],tmpa+1,ppm[tmpa]);
  fprintf(f_ou2 ,"The distance between the closest regions is %.3lf Hz (between R%d:%lf and R%d:%lf ppm)\n",tmpd,tmpi+1,ppm[tmpi],tmpa+1,ppm[tmpa]);

/*fprintf(stdout,"for required resolution, need %10.5lf pt and dr=%lf\n",(fadr*sw1h_2dr)*4/(tmpd),1/(4/(tmpd)));*//* ddump */
/*fprintf(f_ou2 ,"for required resolution, need %10.5lf pt and dr=%lf\n",(fadr*sw1h_2dr)*4/(tmpd),1/(4/(tmpd)));*//* ddump */

i=tmpi;j=tmpa;td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);sw=sw1h_2dr;
/*includes width due to lw */
tmpd=fabs(ol[i]-ol[j])-(falv*(addelta[i]+addelta[j])+2.0*addr);

/*increasea little bit the spectralwith to avoir alias of first and list signals*/
td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
sw=sw1h_2dr+(falv*(addelta[0]+addelta[nbc-1])+4.0*sw/td*fadr+2.0*addr);sw1h_2dr=sw;
td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
tmpd=fabs(ol[i]-ol[j])-(falv*(addelta[i]+addelta[j])+2.0*addr);
td=(int)((fadr*sw1h_2dr)*4/(tmpd)+1);
td=(int)((td+1)/2);/*for even number*/
td=(int)(2*td);/*for even number*/


refmaxt1=(double)td/sw1h_2dr/2.0;
fprintf(f_ou2 ,"For required resolution a full spectrum would need TD0=%d pt and SW0=%.3lf Hz (%.6lf ppm) (dr=%lf, maxt1=%lf s)\n",td,sw1h_2dr,sw1h_2dr/in_freq,sw1h_2dr/td,refmaxt1);/* ddump */
//fprintf(f_ou2 ,"\nA spectrum with the following acquisition parameters resolve all regions in the carbon dimension\n");
//fprintf(stdout,"For required resolution a full spectrum would need TD0=%d pt and SW0=%.6lf (dr=%lf, maxt1=%lf)\n",td,sw1h_2dr,sw1h_2dr/td,refmaxt1);/* ddump */

/* for showing data for full spectrum as well */
if(!(singp!=0.0 && singptd!=0) )
if(strstr(opt,"sw0")!=0) dump2(sw1h_2dr,td,(int)pow(2,((int)(log(td-1)/log(2)))+2),&ol[0],&addelta[0],&ppm[0],in_freq*0.5*(min+max),"sw0o1",bufh,bufg,bufftma,in_o2, in_refd1, in_refd2, in_refd3,in_freq,in_sr,si2,refmaxt1);


if(singp!=0.0 && singptd!=0) {

dump2(singp,singptd,in_si,&ol[0],&addelta[0],&ppm[0],o2,"sp",bufh,bufg,bufftma,in_o2, in_refd1, in_refd2, in_refd3,in_freq,in_sr,si2,refmaxt1);

}else{

tmpd2=0.0;
tt1=time(NULL);
/*init*/
/*for(i=0;i<nbc;i++) { fprintf(stdout,"i=%d %lf == %d",i, *(protdim+5000*i),(int)*(protdim+5000*i));}*/


//
//
//
//protdim
//
//
fclose(f_out);
fclose(f_ou2);
sprintf(buff,"cat %s|Mail -s \"Results of simplealias 2.0 for %s acqu:%s proc:%s\" %s ",bufftmp,in_refd1,in_refd2,in_refd3,in_email);system(buff);
sprintf(buff,"cat %s|Mail -s \"Copy of results sent to %s of simplealias 2.0 for %s acqu:%s proc:%s\" %s ",bufftmp,in_email,in_refd1,in_refd2,in_refd3,"damien.jeannerat@chiorg.unige.ch");system(buff);

return(0);

}
}